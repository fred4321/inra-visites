import grails.converters.JSON
import org.codehaus.groovy.grails.commons.*
//import org.grails.mandrill.MandrillMessage
//import org.grails.mandrill.MandrillRecipient
import javax.mail.internet.*;
import javax.mail.*
import javax.activation.*

class MessageService {
    def mailService
    
    def prepareMessage(User user, emails, sujet, message) {
        def msg_api = [
            message:message,
            subject:sujet,
            from:user.email ? user.email : "admin@inra.fr",
            to:emails.toArray(),
        ]
        return msg_api
    }
    
    def send(msg){
        println "INFO : "+ new Date().toString() + " Envoi du message :" + msg.subject + " from : " + msg.from + " to :" +msg.to
        mailService.sendMail {
            to msg.to
            from msg.from
            subject msg.subject
            html msg.message
        }
    }
    
    def vmConvocation(VM vm){
        return this.entete(vm.agent) +
        "<p>Veuillez vous présenter le <b>" + vm.date.format('dd/MM/yyyy') + " à "+vm.creneau+"</b> à "+vm.lieu+" pour une "+Params.findByValeur(vm.natureVM).info1+".</p>"+
        "<p>Au cas où sous seriez dans l'impossibilité de vous rendre à cette convocation, il est impératif que vous informiez votre gestionnaire d'unité afin de vous faire remplacer.</p>"+
        "<h3>Important :</h3>"+
        "<ul>"+
            "<li>Apporter un flacon d'urine, le carnet de santé ou de vaccination.</li>"+
            "<li>Pensez à votre fiche liaison qui  est un outil indispensable pour que le médecin de prévention ait connaissance de vos expositions et puisse évaluer les risques potentiels pour votre santé.</br>Remplissez-la avec soin et n’oubliez pas de l’apporter au médecin de prévention au moment de la visite médicale.</li>"+
            "<li>Si vous venez de changer de poste de travail ou si vous êtes nouveau recruté(e), n’hésitez pas à contacter le secrétariat de votre unité pour en avoir un exemplaire ou bien vous pouvez aller la télécharger sur le site : <a href='https://intranet6.inra.fr/prevention/ACCES-PAR-THEME/Sante-au-travail'>https://intranet6.inra.fr/prevention/ACCES-PAR-THEME/Sante-au-travail</a></li>"+
            "<li>Si vous êtes convoqué(e) à une visite médicale de fin d'activité, préparer votre visite médicale en contactant votre chargé prévention d'unité qui vous guidera pour les documents à compléter (ex : attestation d'exposition,...) ; documents nécessaires pour le médecin afin d'optimiser la visite médicale.</li>"+
        "</ul></br>"+\
        "<p>Cordialement.</p>"+
        "<p>Le service du personnel du Centre INRA Bretagne-Basse Normandie</p><br>"+
        "<i>Pensez ENVIRONNEMENT : n'imprimez que si nécessaire</i>"
    }
    
    def bsConvocation(BS bs){
        def msg =  this.entete(bs.agent) +
        "<p>Conformément à l’avis mentionné sur la fiche de visite médicale qui vous a été remise à l’issue de votre dernière visite médicale par le médecin de prévention, vous devez avoir les analyses suivantes :</p>"+
        "<ul>";
        bs.type.split('_').each(){
            msg = msg + "<li>"+it+"</li>"
        }
        msg = msg + "</ul>"+
        "<p>En conséquence, vous devrez vous présenter au rendez-vous suivant :</br>"+
        "<b>Le "+bs.sDatePassage+" à "+bs.lieu+" entre "+ bs.heureDebut +" et "+ bs.heureFin +"</b></p></br>"+
        "<p>Cordialement.</p>"+
        "<p>Le service du personnel du Centre INRA Bretagne-Basse Normandie</p><br>"+
        "<i>Pensez ENVIRONNEMENT : n'imprimez que si nécessaire</i>"
        return msg
    }
    
    def entete(Agent agent){
        def msg = "<p>Madame, Monsieur,</p>"+
                  "<p>"+agent.nom+" "+agent.prenom+", unité : "+agent.unite.nom+"</p>"
        return msg
    }
}

