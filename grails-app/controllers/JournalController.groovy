import groovy.json.JsonSlurper

class JournalController {
    
    def getAll(){
        if(session.user){
            if(params.max && params.offset && params.sort && params.filter){
                def slurper = new JsonSlurper()
                def offset = Integer.valueOf(params.offset)
		def max = Integer.valueOf(params.max)
                def sort = slurper.parseText(params.sort)
                def filter = slurper.parseText(params.filter)
                def sortIndex = 'matricule'
		def sortOrder  = 'asc'
                sort.each() { key, value ->
                    sortIndex=key
                    sortOrder=value
                }
                def journaux = Journal.createCriteria().list(max: max, offset: offset) {
                    if (filter.nom){
                        ilike('nom', "${filter.nom}%")
                    }
                    if (filter.prenom){
                        ilike('prenom', "${filter.prenom}%")
                    }
                    if (filter.matricule){
                        ilike('matricule', "${filter.matricule}%")
                    }
                    if (filter.typeObject){
                        ilike('typeObject', "${filter.typeObject}%")
                    }
                    if (filter.unite){
                        ilike('unite', "${filter.unite}%")
                    }
                    if (filter.user){
                        ilike('user', "%${filter.user}%")
                    }
                    if (filter.operation){
                        ilike('operation', "%${filter.operation}%")
                    }
                    if (filter.ref && filter.ref.isNumber()){
                        eq('ref', filter.ref.toInteger())
                    }
                    if (filter.date){
                        ilike('sDate', "%${filter.date}%")
                    }
                    order(sortIndex, sortOrder)
                }
                if(journaux){
                    return [status:200, data:[journaux:journaux,total: journaux.totalCount]]
                }
                return [status:204]
            }
            return [status:415]
        }
        return [status : 401]
    }
    
    def getTypes(){
        if(session.user){
            if(params.type){
                def type
                switch (params.type){
                case "type":
                    type = "typeObject"
                    break;
                case "unite":
                    type="unite"
                    break;
                case "user":
                    type="user"
                    break;
                default : 
                    return [status : 415]
                }
                def types = Journal.executeQuery("SELECT DISTINCT j."+type+" FROM Journal j ORDER BY j."+type)
                if (types){
                    return [status : 200, data : types]
                }
                return [status : 204]
            }
            return [status : 415]
        }
        return [status : 401]
    }

}
