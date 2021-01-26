/* 
 * Copyright (C) 2014 thurgi
 * 
 * Format de différent paramètres
 * site : string
 * natureVM : string
 * typeBS : string
 * periodicite
 */

angular.module('Configuration',[]).constant('CONST',{
    version:0.1,
    title : 'INRAE',
    baseUri:'/',
    templates:{
        action:'partials/components/actions.html',
        agentForm:'partials/components/formAgent.html',
        pagination:'partials/components/pagination.html',
        remarques:'partials/components/remarques.html',
        import:'partials/components/import.html'
    },
    groupes:['site','natureVM','typeBS','periodicite','lieu','lieuVM','lieuBS','créneau BS','créneau VM', 'délai VM','statistiques','absence','RRH','medecin'],
    profils:['SP','administrateur','GU','medecin'],
    pagination:{
        nbPages:[25, 50, 100, 150, 200, 500,1000],
        maxSize:5
    },
    reloadTimer : 500
});
