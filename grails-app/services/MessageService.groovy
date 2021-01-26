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
<p>Au cas où vous seriez dans l'impossibilité de vous rendre à cette convocation, merci d’informer l’Assistante du Médecin de Prévention par mail : medtlse-medtlse@inrae.fr</p>
<h3>Important :</h3>
<p>Merci de vous munir impérativement :</p>
<ul>
    <li>De la convocation</li>
    <li>De votre fiche de liaison (fiche de prévention des expositions) qui est un outil indispensable pour que le médecin de prévention ait connaissance de vos expositions et puisse évaluer les risques potentiels pour votre santé.<br/>Remplissez-la avec soin et n’oubliez pas de la faire signer et l’apporter au médecin de prévention au moment de la visite médicale.</li>
    <li>Vous devez aller la télécharger sur le site (au format Excel) : https://intranet.inrae.fr/prevention/ACCES-PAR-THEME/Sante-au-travail/Suivi-medical</li>
    <li>Des documents relatifs à votre état de santé (résultat d’examen, compte rendu médical…)</li>
    <li>Du carnet de santé ou de vaccinations.</li>
    <li>De vos lunettes.</li>
  </ul>
  <p>En cas de première visite en vue de la manipulation de radioéléments, merci d’amener une photo d’identité et votre numéro de sécurité sociale.</p>
<p>Cordialement.</p>
<p>Le centre Occitanie-Toulouse</p><br>
<i>Pensez ENVIRONNEMENT : n'imprimez que si nécessaire</i>
""".toString()

// Email pour
/*
        "<p>Veuillez vous présenter le <b>" + vm.date.format('dd/MM/yyyy') + " à "+vm.creneau+"</b> à "+vm.lieu+" pour une "+Params.findByValeur(vm.natureVM).info1+".</p>"+
        "<p>Au cas où sous seriez dans l'impossibilité de vous rendre à cette convocation, il est impératif que vous informiez votre gestionnaire d'unité afin de vous faire remplacer.</p>"+
        "<h3>Important :</h3>"+
        "<ul>"+
            "<li>Apporter un flacon d'urine, le carnet de santé ou de vaccination.</li>"+
            "<li>Pensez à votre fiche liaison qui  est un outil indispensable pour que le médecin de prévention ait connaissance de vos expositions et puisse évaluer les risques potentiels pour votre santé.</br>Remplissez-la avec soin et n’oubliez pas de l’apporter au médecin de prévention au moment de la visite médicale.</li>"+
            "<li>Si vous venez de changer de poste de travail ou si vous êtes nouveau recruté(e), n’hésitez pas à contacter le secrétariat de votre unité pour en avoir un exemplaire ou bien vous pouvez aller la télécharger sur le site : <a href='https://intranet6.inrae.fr/prevention/ACCES-PAR-THEME/Sante-au-travail'>https://intranet6.inrae.fr/prevention/ACCES-PAR-THEME/Sante-au-travail</a></li>"+
            "<li>Si vous êtes convoqué(e) à une visite médicale de fin d'activité, préparer votre visite médicale en contactant votre chargé prévention d'unité qui vous guidera pour les documents à compléter (ex : attestation d'exposition,...) ; documents nécessaires pour le médecin afin d'optimiser la visite médicale.</li>"+
        "</ul></br>"+\
        "<p>Cordialement.</p>"+
        "<p>${grailsApplication.config.signature}</p><br>"+
        "<i>Pensez ENVIRONNEMENT : n'imprimez que si nécessaire</i>"
*/
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

