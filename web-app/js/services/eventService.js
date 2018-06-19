angular.module('inra').service('EventService',['$http','CONST', function($http,CONST) {

    var url = CONST.url.event;

    this.get = function() {
        // $http returns a promise, which has a then function, which also returns a promise
        var promise = $http.get(url, {cache: true}).then(function(response) {
            // The return value gets picked up by the then in the controller.
            return response.data.events;
        });
        // Return the promise to the controller
        return promise;
    };

    /* Prepare Events Source as FullCalander Event Object */
    this.prepareEvents = function(events) {

        // Store Events
        var eventsStore = [];

        // Loop through the events data
        for (var key in events) {

            // create a new show object with needed data
            // i.e. id, title, start
            var show = {
                id: parseInt(events[key].show.id),
                title: events[key].show.title,
                start: events[key].show.start,
                url: '#/calendar/' + events[key].show.id
            };

            // Push the new show to eventsStore events object
            eventsStore.push(show);
        }
        //console.log('hey', eventsStore)
        return eventsStore;
    };

}]);