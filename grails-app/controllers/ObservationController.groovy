
import java.sql.SQLException

class ObservationController {
    
    def addObservation(){
        if(session.user){
            def data = request.JSON//méthode qui récupère du json
            if(data && data.id){
                def vm = VM.get(data.id)
                if(vm){
                    def obs = new Observation()
                    obs.save()
                    vm.addToObservations(obs)
                    vm.save()
                    return [status : 200, data : obs]
                }
                return [status : 204]
            }
            return [status : 415]
        }
        return [status :401]
    }
    
    def addRemarque(){
        if(session.user){
            def data = request.JSON//méthode qui récupère du json
            if(data && data.id){
                def bs = BS.get(data.id)
                if(bs){
                    def c = new Observation()
                    c.save()
                    bs.addToRemarques(c)
                    bs.save()
                    return [status : 200, data : c]
                }
                return [status : 204]
            }
            return [status : 415]
        }
        return [status :401]
    }
    
    def addConclusion(){
        if(session.user){
            def data = request.JSON//méthode qui récupère du json
            if(data && data.id){
                def vm = VM.get(data.id)
                if(vm){
                    def c = new Observation()
                    c.save()
                    vm.addToConclusions(c)
                    vm.save()
                    return [status : 200, data : c]
                }
                return [status : 204]
            }
            return [status : 415]
        }
        return [status :401]
    }

    def getAllObservation(){
        if(session.user){
            def id = params.id
            if(id){
                def vm = VM.get(id)
                if(vm && vm.observations){
                    return [status : 200, data : vm.observations]
                }
                return [status : 204]
            }
            return [status : 415]
        }
        return [status :401]
    }
    
    def getAllRemarque(){
        if(session.user){
            def id = params.id
            if(id){
                def bs = BS.get(id)
                if(bs && bs.remarques){
                    return [status : 200, data : bs.remarques]
                }
                return [status : 204]
            }
            return [status : 415]
        }
        return [status :401]
    }
    
    def getAllConclusion(){
        if(session.user){
            def id = params.id
            if(id){
                def vm = VM.get(id)
                if(vm && vm.conclusions){
                    return [status : 200, data : vm.conclusions]
                }
                return [status : 204]
            }
            return [status : 415]
        }
        return [status :401]
    }
    
    def update(){
        if(session.user){
            def data = request.JSON//méthode qui récupère du json
            println data
            if(data && data.id){
                def o = Observation.get(data.id)
                if(o){
                    o.text = data.text ? data.text : ""
                    if(o.validate()){
                        o.save()
                        log.info("mise a jour de l'observation "+o.id)
                        return [status : 200, data : o]
                    }
                    return [status : 500]
                }
                return [status : 204]
            }
            return [status : 415]
        }
        return [status :401]
    }
    
    def removeObservation(){
        if(session.user){
            def data = request.JSON//méthode qui récupère du json
            if ( data && data.id && data.cId){
                def vm = VM.get(data.id)
                def c = Observation.get(data.cId)
                if(vm && c){
                    try{
                        vm.removeFromObservations(c)
                        vm.save()
                        c.delete()
                        return [status : 200]
                    }catch(SQLException ex){
                        return [status : 500]
                    }
                }
                return [status : 204]
            }
            return [status : 415]
        }
        return [status :401]
    }
    
    def removeRemarque(){
        if(session.user){
            def data = request.JSON//méthode qui récupère du json
            if ( data && data.id && data.cId){
                def bs = BS.get(data.id)
                def c = Observation.get(data.cId)
                if ( bs && c){
                    try{
                        bs.removeFromRemarques(c)
                        bs.save()
                        c.delete()
                        return [status : 200]
                    }catch(SQLException ex){
                        return [status : 500]
                    }
                }
                return [status : 204]
            }
            return [status : 415]
        }
        return [status :401]
    }
    
    def removeConclusion(){
        if(session.user){
            def data = request.JSON//méthode qui récupère du json
            if ( data && data.id && data.cId){
                def vm = VM.get(data.id)
                def c = Observation.get(data.cId)
                if(vm && c){
                    try{
                        vm.removeFromConclusions(c)
                        vm.save()
                        c.delete()
                        return [status : 200]
                    }catch(SQLException ex){
                        return [status : 500]
                    }
                }
                return [status : 204]
            }
            return [status : 415]
        }
        return [status :401]
    }
}
