class GroupeController {

    def getAll() {
        if(session.user) {
            def groupes = Groupe.All()
            if (groupes) {
                return [status : 200, data : groupes]
            }
            return [status : 204]
        }
        return [status : 401]
    }

    def get() {
        if(session.user) {
            if(params.id) {
                def groupe = Groupe.get(params.id)
                if ( groupe ) {
                    return [status : 200, data : groupe.getPropertiesForOne()]
                }
                return [status : 204]
            }
            return [status : 415]
        }
        return [status :401]
    }

    def post() {
        if(session.user && session.user.profil == "administrateur") {
            def data = request.JSON
            if(data && data.nom && data.email) {
                def groupe = new Groupe( nom : data.nom, email : data.email)
                groupe.libelle = data?.libelle
                if( data.agents && data.agents.length() > 0 ) {
                    data.agents.each() {
                        def agent = Agent.get(it.id)
                        if (agent) {
                            groupe.addToAgents(agent)
                        }
                    }
                }
                if(groupe.validate()) {
                    groupe.save()
                    log.info("creation du groupe " + groupe.id)
                    return [status : 200, data : groupe.getPropertiesForOne()]
                }
                return [status : 500]
            }
            return [status : 415]
        }
        return [status :401]
    }

    def delete = {
        if(session.user && session.user.profil == "administrateur") {
            if(params.id) {
                def groupe = Groupe.get(params.id)
                if(groupe) {
                    log.info("suppression du groupe " + groupe.id)
                    groupe.delete(flush:true)
                    return [status : 200, data : null]
                }
                return [status : 204]
            }
            return [status : 415]
        }
        return [status :401]
    }

    def update = {
        if(session.user && session.user.profil == "administrateur") {
            def data = request.JSON
            if(data && data.nom && data.email && data.id ) {
                def groupe = Groupe.get( data.id )
                if(groupe) {
                    groupe.nom = data.nom
                    groupe.email = data.email
                    groupe.libelle = data?.libelle
                    groupe.agents.clear()
                    if( data.agents && data.agents.length() > 0 ) {
                        data.agents.each() {
                            def agent = Agent.get(it.id)
                            if (agent) {
                                groupe.addToAgents(agent)
                            }
                        }
                    }
                    if(groupe.validate()) {
                        groupe.save()
                        log.info("mise a jour du groupe " + groupe.id)
                        return [status : 200, data : groupe.getPropertiesForOne()]
                    }
                    return [status : 500]
                }
                return [status : 204]
            }
            return [status : 415]
        }
        return [status :401]
    }

}
