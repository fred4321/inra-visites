
import org.apache.poi.xssf.usermodel.XSSFCell
import org.apache.poi.xssf.usermodel.XSSFRow
import org.apache.poi.xssf.usermodel.XSSFSheet
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import groovy.json.JsonSlurper
import java.text.SimpleDateFormat
import java.nio.file.Files
import org.apache.commons.io.FileUtils
import org.apache.poi.openxml4j.opc.OPCPackage

class ImportController {

    def ImportService
    def TransfertService

    def getVerification() {
        if(session.user) {
            if(params.id) {
                def verif  = VerificationImport.getRapportVerification(params.id)
                if(verif) {
                    return [status:200, data: verif]
                }
                return [status:204]
            }
            return [status:406]
        }
        return [status: 401]
    }

    def GetAllVerification() {
        if(session.user) {
            if(params.max && params.offset && params.sort && params.filter) {
                def slurper = new JsonSlurper()
                def offset = Integer.valueOf(params.offset)
                def max = Integer.valueOf(params.max)
                def sort = slurper.parseText(params.sort)
                def filter = slurper.parseText(params.filter)
                def sortIndex = 'id'
                def sortOrder  = 'asc'
                sort.each() { key, value ->
                    sortIndex = key
                    sortOrder = value
                }
                def verifs = VerificationImport.createCriteria().list(max: max, offset: offset) {
                    if (filter.id && filter.id ==~ /[0-9]*/ ) {
                        eq('id', Long.valueOf(filter.id))
                    }
                    if (filter.date) {
                        ilike('sDate', "%${filter.date}%")
                    }
                    if (filter.type) {
                        ilike('type', "%${filter.type}%")
                    }
                    if (filter.statut) {
                        ilike('statut', "%${filter.statut}%")
                    }
                    order(sortIndex, sortOrder)
                }
                if(verifs) {
                    def listVerif = verifs.collect {
                        return it.getDefaultProperties()
                    }
                    return [status:200, data:[verifs:listVerif, total: verifs.totalCount]]
                }
                return [status:204]
            }
            return [status:415]
        }
        return [status: 401]
    }

    def GetImport() {
        if(session.user) {
            if(params.id) {
                def rapport  = Rapport.get(params.id)
                if(rapport) {
                    return [status:200, data: rapport.getRapportImport()]
                }
                return [status:204]
            }
            return [status:406]
        }
        return [status: 401]
    }

    def GetAllImport() {
        if(session.user) {
            if(params.max && params.offset && params.sort && params.filter) {
                def slurper = new JsonSlurper()
                def offset = Integer.valueOf(params.offset)
                def max = Integer.valueOf(params.max)
                def sort = slurper.parseText(params.sort)
                def filter = slurper.parseText(params.filter)
                def sortIndex = 'id'
                def sortOrder  = 'asc'
                sort.each() { key, value ->
                    sortIndex = key
                    sortOrder = value
                }
                def rapports = Rapport.createCriteria().list(max: max, offset: offset) {
                    if (filter.id && filter.id ==~ /[0-9]*/ ) {
                        eq('id', Long.valueOf(filter.id))
                    }
                    if (filter.date) {
                        ilike('sDate', "%${filter.date}%")
                    }
                    if (filter.type) {
                        ilike('type', "%${filter.type}%")
                    }
                    if (filter.statut) {
                        ilike('statut', "%${filter.statut}%")
                    }
                    order(sortIndex, sortOrder)
                }
                if(rapports) {
                    def listRapport = rapports.collect {
                        return it.getDefaultProperties()
                    }
                    return [status:200, data:[rapports:listRapport, total: rapports.totalCount]]
                }
                return [status:204]
            }
            return [status:415]
        }
        return [status: 401]
    }

    def upload() {
        if(params.name) {
            def f = request.getFile(params.name)
            if(f != null && !f.empty) {
                if(params.name == "WACAT027" || params.name == "WADAA06C" || params.name == "WACAT078") {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss")
                    Date date = new Date()
                    File src = new File("import/verification/" + params.name)
                    println "import/save/" + params.name + dateFormat.format(date)
                    File dest = new File("import/save/" + params.name + dateFormat.format(date))
                    f.transferTo(src)

                    FileUtils.copyFile(src, dest)//Apache Commons IO
                    //Files.copy(src, dest) //java7
                    return [status:200]
                }
            }
        }
        return [status:204]
    }

    def verif() {
        def mapResult
        if(params.name) {
            def file = new File("import/verification/" + params.name)
            if(file.exists()) {
                def xlsx = OPCPackage.open(file)
                XSSFWorkbook  workbook = new XSSFWorkbook(xlsx)
                XSSFSheet sheet = workbook.getSheetAt(0)
                def lastRowNum = sheet.getLastRowNum()
                def verif = ImportService.check(params.name, lastRowNum, sheet)
                xlsx.close()
                if(!verif.stack) {
                    verif.statut = "OK"
                }
                if(verif.validate()) {
                    verif.save()
                    if(verif.statut == "OK") {
                        return [status:200]
                    }
                    return [status:204]
                }
            }
            return [status:204]
        }
        return [status:406]
    }

    def importation() {
        def mapResult
        if(params.name) {
            def file = new File("import/verification/" + params.name)
            if(file.exists()) {
                def xlsx = OPCPackage.open(file)
                XSSFWorkbook workbook = new XSSFWorkbook(xlsx)
                XSSFSheet sheet = workbook.getSheetAt(0)
                def lastRowNum = sheet.getLastRowNum()
                def status
                if(params.name == "WACAT027") {
                    status = ImportService.importWACAT027(sheet, lastRowNum)
                }
                else if(params.name == "WADAA06C") {
                    status = ImportService.importWADAA06C(sheet, lastRowNum)
                }
                else if(params.name == "WACAT078") {
                    status = ImportService.importWACAT078(sheet, lastRowNum)
                }
                xlsx.close()
                return [status : status]
            }
            return [status : 204]
        }
        return [status : 415]
    }

    def importToulouse() {
        def file = new File("importToulouse2.xlsx")
        if(file.exists()) {
            def xlsx = OPCPackage.open(file)
            XSSFWorkbook workbook = new XSSFWorkbook(xlsx)
            XSSFSheet sheet = workbook.getSheetAt(0)
            def lastRowNum = sheet.getLastRowNum()

            (1..lastRowNum).each {
                XSSFRow row = sheet.getRow(it)
                XSSFCell matricule =   row.getCell(0)
                XSSFCell dateSortie =   row.getCell(9)
                XSSFCell site =   row.getCell(8)
                XSSFCell unite =   row.getCell(6)
                def dateDerniereVM

                def agent = Agent.findByMatricule(matricule.toString().trim())
                println agent
                if(agent) {
                    try {
                        dateSortie = dateSortie.getDateCellValue()
                        agent.dateSortie = dateSortie
                    }catch (e) {
                        println "Problème de date pour ${matricule}"
                    }

                    if(unite) {
                        def uniteFind = Unite.findByNom(unite.toString())
                        if(uniteFind) {
                            agent.unite = uniteFind
                            def guFind = User.findByUnite(uniteFind)
                            if(guFind) {
                                agent.gu = guFind
                            }
                        }
                    }

                    if(site) {
                        agent.site = site.toString()
                    }

                    agent.save()
                    println agent.errors
                }else {
                    println "Impossible de trouver $matricule"
                }
            }

            xlsx.close()
            return [status : 200]
        }
    }

//    def transfert(){
//        TransfertService.readDataBase()
//        return [status : 200]
//    }

}
