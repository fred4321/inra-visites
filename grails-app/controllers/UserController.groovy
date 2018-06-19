class UserController {
    
    def getAll(){
        if(session.user){
            def users = User.All()
            if(users){
                return [status : 200, data : users]
            }
            return [status : 204]
        }
        return [ status : 401 ]
    }
    
    def getGu(){
        if(session.user){
            def users = User.getGu()
            if(users){
                return [status : 200, data : users]
            }
            return [status : 204]
        }
        return [ status : 401 ]
    }
    
    def setPassword(){
        if(session.user){
            def data = request.JSON
            if(data.hold && data.new1 && data.new2){
                if(data.new1 == data.new2){
                    if(data.hold == session.user.password){
                        session.user.password = data.new1
                        if(session.user.validate()){
                            session.user.save()
                            return [status:200]
                        }
                        return [status:500]
                    }
                    return [status:409]
                }
                return [status:412]
            }
            return [status:415]
        }
        return [status: 401]
    }
    
    def post(){
        if(session.user){
            def data = request.JSON
            def user = new User()
            if(data && data.identifiant && data.password && data.profil && data.identifiant.size()>0 && data.password.size()>0){
                user.identifiant=data.identifiant
                user.password=data.password
                user.email=data.email
                user.profil=data.profil
                if(user.validate()){
                    println "password after validate : " + user.password
                    user.save(flush:true)
                    if(data.unite){
                        def unite = Unite.findByNom(data.unite)
                        if(unite){
                            unite.addToGu(user)
                            if(unite.validate()){
                                unite.save()
                                log.info("enregistrement de l'utilisateur "+data.identifiant)
                                return [status : 200, data : user.getDefaultProperties()]
                            }
                            return [status : 501 , data : user.getDefaultProperties()]
                        }
                        return [status : 204]
                    }
                    return [status : 200, data : user.getDefaultProperties()]
                }
                return [status : 500]
            }
            return [status : 415]
        }
        return [ status : 401 ]
    }
    
    def delete() {
        if(session.user){
            if (params.id && params.id.isInteger()){
                def user = User.get(params.id)
                if(user){
                    def agents  = Agent.findAllWhere(gu:user)
                    agents.each{
                        it.gu = null
                        it.save(flush:true)
                    }
                    user.delete(flush:true)
                    log.info("suppression de l'utilisateur "+params.id)
                    return [status : 200]
                }
                return [status : 204]
            }
            return [status : 415]
        }
        return [ status : 401 ]
    }
    
    def update() {
        if(session.user){
            def data = request.JSON
            if(data && data.id){
                def user = User.get(data.id)
                user.identifiant= data.identifiant ? data.identifiant : user.identifiant
                user.profil= data.profil ? data.profil : user.profil
                user.email= data.email ? data.email : user.email
                user.password = data.password ? data.password : user.password
                if(user.validate()){
                    user.save(flush:true)
                    if(data.unite){
                        def unite = Unite.findByNom(data.unite)
                        if(unite){
                            unite.addToGu(user)
                            if(unite.validate()){
                                unite.save()
                                return [status : 200 ,data : user.getDefaultProperties()]
                            }
                            return [status : 501]
                        }
                        return [status : 204]
                    }
                    return [status : 200 ,data : user.getDefaultProperties()]
                }
                user.errors.allErrors.each{ println it }
            }
            return [status : 415]
        }
        return [ status : 401 ]
    }
	
    def login = {
        def data = request.JSON
        def user = User.findByIdentifiant(data.identifiant)
        def mapResult= [:]
        if(user) {
            if(data.password == user.password) {
                session.setMaxInactiveInterval(6000);
                session.user = user
                log.info("connection de : "+ data.identifiant)
                return [status : 200, data :user.getDefaultProperties()]
            }
            return [status : 203]
        }
        return [status : 204]
    }
	
    def logout = {
        log.info("deconnexion de : "+session.user?.identifiant)
        session.invalidate()
        return [status : 200, data : [identifiant : null , profil : null ]] 
    }
    
    def isLogin = {
        if(session.user){
            return [status : 200, data : [identifiant : session.user?.identifiant, profil : session.user?.profil ] ]
        }
        return [status : 204, data : [identifiant : null , profil : null ]]      
    }
}
