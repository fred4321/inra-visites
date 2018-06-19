class ParamsController {
	
    def getAll(){
        if(session.user){
            def param
            if(params.groupe) {
                param = Params.findAllWhere(groupe:params.groupe) 
            }
            else {
                param=Params.findAll()
            }
            if (param){
                return [status : 200, data : param]
            }
            return [status : 204]
        }
        return [ status : 401 ]
    }
	
    def get() {
        if(session.user){
            def param = Params.find(params.id)
            if (param){
                return [status : 200, data : param]
            }
            return [status : 204]
        }
        return [ status : 401 ]
    }
	
    def post(){
        if(session.user){
            def param = new Params(request.JSON)
            def mapResult
            param.properties = params
            if(param.validate()) {
                param.save()
                return [status : 200, data : param]
            }
            else {
                param.errors.allErrors.each{ println it }
                return [status : 500]
            }
        }
        return [ status : 401 ]
    }
	
    def update() {
        if(session.user){
            def data = request.JSON//méthode qui récupère du json
            if(data && data.id) {
                def param = Params.get(data.id)
                param.properties = data
                if(param.validate()) {
                    param.save(flush:true)
                    return [status : 200, data : param ] 
                }
                else {
                    param.errors.allErrors.each{ println it }
                    return [status : 500]
                }
            }
            return [status : 415]
        }
        return [status :401]
    }
	
    def delete() {
        if(session.user){
            def param = Params.get(params.id)
            def mapResult
            if(param) {
                param.properties = params
                if(param.validate()) {
                    param.delete(flush:true)
                    return [status : 200, data : param]
                }
                else {
                    param.errors.allErrors.each{ println it }
                    return [status : 500]
                }
            }
            return [status : 415]
        }
        return [status :401]
    }
    
    def getLibEmploi(){
        if(session.user){
            def param = Agent.executeQuery("SELECT DISTINCT a.libEmploi FROM Agent a ORDER BY a.libEmploi")
            if (param){
                return [status : 200, data : param]
            }
            return [status : 204]
        }
        return [ status : 401 ]
    }
}
