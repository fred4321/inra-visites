class UniteController {

    def index() { }
	
    def getAll(){
        if(session.user){
            def unites = Unite.findAll()
            def listUnites = unites.collect{unite ->
                return unite.getDefaultProperties()
            }
            if(listUnites){
                return [status : 200, data : listUnites]
            }
            return [status : 204]
        }
        return [ status : 401 ]
    }
	
    def get() {
        if(session.user){
            def unite = Unite.get(params.id)
            if(unite){
                return [status : 200, data : unite]
            }
            return [status : 204]
        }
        return [ status : 401 ]
    }
	
    def post(){
        if(session.user){
            def data = request.JSON
            if(data && data.site && data.lieu && data.nom && data.DU ) {
                    def unite = new Unite(nom : data.nom, lieu : data.lieu , site : data.site , DU : data.DU )
                    if(unite.validate()){
                        unite.save()
                        return [status : 200, data : unite.getDefaultProperties()]
                    }else{
                        unite.errors.allErrors.each{ println it }
                        return [status : 500]
                    }
            }
            return [status : 415]
        }
        return [ status : 401 ]
    }
	
    def update() {
        if(session.user){
            def unite = Unite.get(params.id)
            def data = request.JSON
            if(unite && data.nom && data.lieu && data.site && data.DU ) {
                unite.nom = data.nom
                unite.site = data.site
                unite.lieu = data.lieu
                unite.DU = data.DU
                if(unite.validate()) {
                    unite.save(flush:true)
                    return [status : 200, data : unite.getDefaultProperties()]
                }
                else {
                    unite.errors.allErrors.each{ println it }
                    return [status : 500]
                }
            }
            return [status : 204]
        }
        return [ status : 401 ]
    }
	
    def delete() {
        if(session.user){
            def unite = Unite.get(params.id)
            if(unite) {
                def agentAttach = Agent.findAllWhere(unite : unite)
                def userAttach = User.findAllWhere(unite : unite)
                if(agentAttach.size()==0 && userAttach.size()==0) {
                    def vms = VM.findAllByUnite(unite)
                    if(vms){
                        vms.each{
                            it.unite = null
                            it.save()
                        }
                    }
                    unite.agent=null
                    unite.gu=null
                    if(unite.validate()){
                        unite.save(flush:true)
                        unite.delete(flush:true)
                        return [status : 200]
                    }
                    unite.errors.each{println it}
                    return [status : 500]
                }
                return [status : 409, data : [agents : agentAttach, users : userAttach]]
            }
            return [status : 204]
        }
        return [ status : 401 ]
    }
}
