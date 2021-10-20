
class PdfController {

    def ficheVM() {
        if(session.user) {
            if(params.id) {
                def vm
                if(session.user.profil == "medecin" || session.user.profil == "SP") {
                    vm = VM.get(params.id).getFullVm()
                }else {
                    vm = VM.get(params.id).getLimitVm()
                }
                if(vm) {                
                    renderPdf(template:'vm', model: [vm:vm, user:session.user, pdf:true], filename : "vm-" + vm.id + ".pdf")
                    log.info("impression de la vm " + vm.id + " pour " + session.user.identifiant)
                }
                return [status : 204]
            }
            return [status : 415]
        }
        return [status :401]
    }

    def ficheBS() {
        if(session.user && (session.user.profil == "medecin" || session.user.profil == "SP")) {
            if(params.id) {
                def bs = BS.get(params.id)
                if(bs) {
                    def properties = bs.getDefaultProperties()
                    properties.remarques = bs.getRemarquesProperties()
                    renderPdf(template:'bs', model: [bs:properties], filename : "bs-" + bs.id + ".pdf")
                    log.info("impression du bs " + bs.id + " pour " + session.user.identifiant)
                }
                return [status : 204]
            }
            return [status : 415]
        }
        return [status :401]
    }

    def ficheAgent() {
        if(session.user) {
            if(params.id) {
                def agent = Agent.get(params.id)
                if(agent) {
                    renderPdf(template:'agent', model: [agent:agent], filename : "agent-" + agent.id + ".pdf")
                    log.info("impression de l'agent " + agent.id + " pour " + session.user.identifiant)
                }
                return [status : 204]
            }
            return [status : 415]
        }
        return [status :401]
    }

    def rapportVerif() {
        if(session.user) {
            if(params.id) {
                def verif = VerificationImport.getRapportVerification(params.id)
                if(verif) {
                    renderPdf(template:'verification/verification', model: [verification: verif, resume:params.resume, lignes:params.lignes, champs:params.champs, vides:params.vides, invalides:params.invalides, DBB:params.DBB], filename: "verification.pdf")
                }
                return [status : 204]
            }
            return [status : 415]
        }
        return [status :401]
    }

    def rapportImport() {
        if(session.user) {
            if(params.id) {
                def rapport = Rapport.get(params.id)
                if(rapport) {
                    renderPdf(template:'import/rapport', model: [
                            rapport: rapport.getRapportImport(),
                            resume:params.resume,
                            nouveau:params.nouveau,
                            modifier:params.modifier,
                            prioritaire:params.prioritaire,
                            erreur:params.erreur
                        ], filename: "RapportImport" + params.id + ".pdf")
                }
                return [status : 204]
            }
            return [status : 415]
        }
        return [status :401]
    }

    def tableau() {
        if(session.user) {
            def data = request.JSON
            if(data && data.colonne && data.title && data.titles && data.data && data.colonne == data.titles.size()) {
                renderPdf(template:'tableau', model: data, filename: data.title + ".pdf")
            }
            return [status : 415]
        }
        return [status :401]
    }

}
