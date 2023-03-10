// locations to search for config files that get merged into the main config;
// config files can be ConfigSlurper scripts, Java properties files, or classes
// in the classpath in ConfigSlurper format

// grails.config.locations = [ "classpath:${appName}-config.properties",
//                             "classpath:${appName}-config.groovy",
//                             "file:${userHome}/.grails/${appName}-config.properties",
//                             "file:${userHome}/.grails/${appName}-config.groovy"]

// if (System.properties["${appName}.config.location"]) {
//    grails.config.locations << "file:" + System.properties["${appName}.config.location"]
// }

grails.project.groupId = appName // change this to alter the default package name and Maven publishing destination

// The ACCEPT header will not be used for content negotiation for user agents containing the following strings (defaults to the 4 major rendering engines)
grails.mime.disable.accept.header.userAgents = ['Gecko', 'WebKit', 'Presto', 'Trident']
grails.mime.types = [ // the first one is the default format
    all:           '*/*', // 'all' maps to '*' or the first available format in withFormat
    atom:          'application/atom+xml',
    css:           'text/css',
    csv:           'text/csv',
    form:          'application/x-www-form-urlencoded',
    html:          ['text/html', 'application/xhtml+xml'],
    js:            'text/javascript',
    json:          ['application/json', 'text/json'],
    multipartForm: 'multipart/form-data',
    rss:           'application/rss+xml',
    text:          'text/plain',
    hal:           ['application/hal+json', 'application/hal+xml'],
    xml:           ['text/xml', 'application/xml']
]

// URL Mapping Cache Max Size, defaults to 5000
//grails.urlmapping.cache.maxsize = 1000

// Legacy setting for codec used to encode data with ${}
grails.views.default.codec = "html"

// The default scope for controllers. May be prototype, session or singleton.
// If unspecified, controllers are prototype scoped.
grails.controllers.defaultScope = 'singleton'

// GSP settings
grails {
    views {
        gsp {
            encoding = 'UTF-8'
            htmlcodec = 'xml' // use xml escaping instead of HTML4 escaping
            codecs {
                expression = 'html' // escapes values inside ${}
                scriptlet = 'html' // escapes output from scriptlets in GSPs
                taglib = 'none' // escapes output from taglibs
                staticparts = 'none' // escapes output from static template parts
            }
        }
    // escapes all not-encoded output at final stage of outputting
    // filteringCodecForContentType.'text/html' = 'html'
    }

    /*
    // TOULOUSE
    mail {
        host = "smtp.inrae.fr"
        port = 587
        username = "mmedtlse"
        password = "b]Quec7u"
        props = ["mail.smtp.starttls.enable":"true",  "mail.smtp.port":"587"]
    }
    */

// RENNES
// mail {
//     host = "smtp-nonaut.inrae.fr"
//     port = 25
//     props = ["mail.smtp.starttls.enable":"false", "mail.smtp.port":"25"]
// }
}

grails.gorm.autoFlush = true

grails.converters.encoding = "UTF-8"
// scaffolding templates configuration
grails.scaffolding.templates.domainSuffix = 'Instance'

// Set to false to use the new Grails 1.2 JSONBuilder in the render method
grails.json.legacy.builder = false
// enabled native2ascii conversion of i18n properties files
grails.enable.native2ascii = true
// packages to include in Spring bean scanning
grails.spring.bean.packages = []
// whether to disable processing of multi part requests
grails.web.disable.multipart = false

// request parameters to mask when logging exceptions
grails.exceptionresolver.params.exclude = ['password']

// configure auto-caching of queries by default (if false you can cache individual queries with 'cache: true')
grails.hibernate.cache.queries = false

// configure passing transaction's read-only attribute to Hibernate session, queries and criterias
// set "singleSession = false" OSIV mode in hibernate configuration after enabling
grails.hibernate.pass.readonly = false
// configure passing read-only to OSIV session by default, requires "singleSession = false" OSIV mode
grails.hibernate.osiv.readonly = false

environments {
    development {
        disable.auto.recompile = false
        grails.logging.jul.usebridge = true

        email_sender = "visites-medicales@rennes.inrae.fr"
        signature = "Le service du personnel du Centre INRAE Bretagne-Basse Normandie"

        grails.mail.host = "smtp-nonaut.inrae.fr"
        grails.mail.port = 25
        grails.mail.props = ["mail.smtp.starttls.enable":"false", "mail.smtp.port":"25"]
    }

    test { // toulouse
        disable.auto.recompile = false
        grails.logging.jul.usebridge = true

        email_sender = "medtlse.medtlse@inrae.fr"
        convocation = """<p>Au cas o?? vous seriez dans l'impossibilit?? de vous rendre ?? cette convocation, merci d???informer l???Assistante du M??decin de Pr??vention par mail : medtlse-medtlse@inrae.fr</p>
        <h3>Important :</h3>
        <p>Merci de vous munir imp??rativement :</p>
        <ul>
            <li>De la convocation</li>
            <li>De votre fiche de liaison (fiche de pr??vention des expositions) qui est un outil indispensable pour que le m??decin de pr??vention ait connaissance de vos expositions et puisse ??valuer les risques potentiels pour votre sant??.<br/>Remplissez-la avec soin et n???oubliez pas de la faire signer et l???apporter au m??decin de pr??vention au moment de la visite m??dicale.</li>
            <li>Vous devez aller la t??l??charger sur le site (au format Excel) : https://intranet.inrae.fr/prevention/ACCES-PAR-THEME/Sante-au-travail/Suivi-medical</li>
            <li>Des documents relatifs ?? votre ??tat de sant?? (r??sultat d???examen, compte rendu m??dical???)</li>
            <li>Du carnet de sant?? ou de vaccinations.</li>
            <li>De vos lunettes.</li>
          </ul>
        <p>Cordialement.</p>"""
        signature = "Le service de sant?? au travail"

        grails.mail.host = "smtp.inrae.fr"
        grails.mail.username = "mmedtlse"
        grails.mail.password = "b]Quec7u"
        grails.mail.port = 587
        grails.mail.props = ["mail.smtp.starttls.enable":"true", "mail.smtp.port":"587"]
    }

    production { // rennes
        disable.auto.recompile = false
        grails.logging.jul.usebridge = true

        email_sender = "visites-medicales@rennes.inrae.fr"
        convocation = """<p>Au cas o?? sous seriez dans l'impossibilit?? de vous rendre ?? cette convocation, il est imp??ratif que vous informiez votre gestionnaire d'unit?? afin de vous faire remplacer.</p>
        <h3>Important :</h3>
        <ul>
            <li>Apporter un flacon d'urine, le carnet de sant?? ou de vaccination.</li>
            <li>Pensez ?? votre fiche liaison qui?? est un outil indispensable pour que le m??decin de pr??vention ait connaissance de vos expositions et puisse ??valuer les risques potentiels pour votre sant??.</br>Remplissez-la avec soin et n???oubliez pas de l???apporter au m??decin de pr??vention au moment de la visite m??dicale.</li>
            <li>Si vous venez de changer de poste de travail ou si vous ??tes nouveau recrut??(e), n???h??sitez pas ?? contacter le secr??tariat de votre unit?? pour en avoir un exemplaire ou bien vous pouvez aller la t??l??charger sur le site??: <a href='https://intranet6.inrae.fr/prevention/ACCES-PAR-THEME/Sante-au-travail'>https://intranet6.inrae.fr/prevention/ACCES-PAR-THEME/Sante-au-travail</a></li>
            <li>Si vous ??tes convoqu??(e) ?? une visite m??dicale de fin d'activit??, pr??parer votre visite m??dicale en contactant votre charg?? pr??vention d'unit?? qui vous guidera pour les documents ?? compl??ter (ex : attestation d'exposition,...) ; documents n??cessaires pour le m??decin afin d'optimiser la visite m??dicale.</li>
        </ul></br>
        <p>Cordialement.</p>"""
        signature = "Le service du personnel du Centre INRAE Bretagne-Basse Normandie"
        

        grails.mail.host = "smtp-nonaut.inrae.fr"
        grails.mail.port = 25
        grails.mail.props = ["mail.smtp.starttls.enable":"false",  "mail.smtp.port":"25"]
    }
}

// log4j configuration
log4j.main = {
    appenders {
        rollingFile name: "inra",
        maxFileSize: 1024,
        file: "logs/inra.log"
    }

    error  'org.codehaus.groovy.grails.web.servlet',        // controllers
           'org.codehaus.groovy.grails.web.pages',          // GSP
           'org.codehaus.groovy.grails.web.sitemesh',       // layouts
           'org.codehaus.groovy.grails.web.mapping.filter', // URL mapping
           'org.codehaus.groovy.grails.web.mapping',        // URL mapping
           'org.codehaus.groovy.grails.commons',            // core / classloading
           'org.codehaus.groovy.grails.plugins',            // plugins
           'org.codehaus.groovy.grails.orm.hibernate',      // hibernate integration
           'org.springframework',
           'org.hibernate',
           'net.sf.ehcache.hibernate'

    info inra : "grails.app"
}

// format de date
grails.databinding.dateFormats = ["yyyy-MM-dd'T'hh:mm:ss'Z'"]
// Added by the Grails Mandrill plugin:
//mandrill {
//    apiKey = "molEqg8CdPO44OPAfLm9wQ"
//}

jasypt {
    algorithm = "PBEWITHSHA256AND128BITAES-CBC-BC"
    providerName = "BC"
    password = "drUwR2nuphepwAustuRAchUb87xUZACEspe_Af"
    keyObtentionIterations = 1000
}
