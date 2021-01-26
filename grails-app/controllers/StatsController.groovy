
import java.lang.reflect.InvocationTargetException
import java.sql.SQLException
import groovy.sql.Sql

class StatsController {
    
    def dataSource
    
    def visiteParSite() {
        if(session.user){
            def vms = VM.executeQuery("SELECT COUNT(*) AS nbVisite, site FROM VM AS v GROUP BY site")
            def result = vms.collect(){
                return [nb : it[0], site : it[1]]
            }
            return [status : 200, data : result]
        }
        return [status : 401]
    }
    
    def visiteParNature() {
        if(session.user){
            def vms = VM.executeQuery("SELECT COUNT(*) AS nbVisite, site, unite, v.natureVM FROM VM AS v GROUP BY v.natureVM, v.site, v.unite")
            def result = vms.collect(){
                println it
                return [nb : it[0], site : it[1], unite : it[2].nom, nature : it[3]]
            }
            return [status : 200, data : result]
        }
        return [status : 401]
    }
    
    def tableaux(){
        if(session.user){
            def requettes = Params.findAllWhere(groupe:"statistiques")
            if(requettes){
                def data = []
                requettes.eachWithIndex (){it, i->
                    def tableau = [
                        title:(it.info1?:i),
                        titles : [],
                        rows : []
                    ]
                    try {
                        new Sql(dataSource).rows(it.valeur).each { row ->
                            def l = [:]
                            row.each {k, v ->
                                l[k] = v instanceof java.sql.Timestamp ? v.format('dd/MM/yyyy') : v.toString()
                                l[k] = l[k] == "null" ? "" : l[k]
                            }
                            tableau.rows << l
                        }
                        tableau.rows[0].each{k, v ->
                                tableau.titles << k
                        }
                        data[i] = tableau
                    }catch(SQLException e){
                        data[i] = "Erreur de syntaxe dans la requête "+it.id
                    }
                }
                return [status: 200, data :data]
            }
            return [status: 204]
        }
        return [status : 401]
    }
    
}
