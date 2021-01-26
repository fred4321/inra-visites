import grails.converters.JSON
import org.codehaus.groovy.grails.commons.*
//import org.grails.mandrill.MandrillMessage
//import org.grails.mandrill.MandrillRecipient
import javax.mail.internet.*
import javax.mail.*
import javax.activation.*

class MessageService {

    def grailsApplication
    def mailService

    def prepareMessage(User user, emails, sujet, message) {
        def msg_api = [
            message:message,
            subject:sujet,
            from:user.email ? user.email : grailsApplication.config.email_sender,
            to:emails.toArray(),
        ]
        return msg_api
    }

    def send(msg) {
        println "INFO : " + new Date().toString() + " Envoi du message :" + msg.subject + " from : " + msg.from + " to :" + msg.to
        mailService.sendMail {
            to msg.to
            from msg.from
            subject msg.subject
            html msg.message
        }
    }

    def vmConvocation(VM vm) {
        return this.entete(vm.agent) +
"""
<p>Veuillez vous présenter le <b> ${vm.date.format('dd/MM/yyyy')} à ${vm.creneau}</b> à ${vm.lieu} pour une ${Params.findByValeur(vm.natureVM).info1}.</p>
${grailsApplication.config.convocation}
<p>${grailsApplication.config.signature}</p><br>
<i>Pensez ENVIRONNEMENT : n'imprimez que si nécessaire</i>
""".toString()
    }

    def bsConvocation(BS bs) {
        def msg =  this.entete(bs.agent) +
        "<p>Conformément à l’avis mentionné sur la fiche de visite médicale qui vous a été remise à l’issue de votre dernière visite médicale par le médecin de prévention, vous devez avoir les analyses suivantes :</p>"+
        "<ul>"
        bs.type.split('_').each() {
            msg = msg + "<li>" + it + "</li>"
        }
        msg = msg + "</ul>" +
        "<p>En conséquence, vous devrez vous présenter au rendez-vous suivant :</br>"+
        "<b>Le "+bs.sDatePassage+" à "+bs.lieu+" entre "+ bs.heureDebut +" et "+ bs.heureFin +"</b></p></br>"+
        "<p>Cordialement.</p>"+
        "<p>${grailsApplication.config.signature}</p><br>"+
        // Attention au service du personnel
        "<i>Pensez ENVIRONNEMENT : n'imprimez que si nécessaire</i>"
        return msg
    }

    def entete(Agent agent) {
        def msg = "<p>Madame, Monsieur,</p>" +
                  "<p>"+agent.nom+" "+agent.prenom+", unité : "+agent.unite.nom+"</p>"
        return msg
    }

}

