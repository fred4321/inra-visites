/* * Copyright (C) 2014 thurgi*/
angular
  .module("Agent", ["ui.bootstrap", "formINRA"])
  .controller("AgentCtrl", [
    "$scope",
    "$modalInstance",
    "InstanceVM",
    "InstanceBS",
    "ReferentielService",
    "items",
    "AgentService",
    "UserService",
    "createDate",
    function (
      $scope,
      $modalInstance,
      InstanceVM,
      InstanceBS,
      ReferentielService,
      items,
      AgentService,
      UserService,
      createDate
    ) {
      $scope.access = false;
      if (UserService.getProfil() === "SP") {
        $scope.access = true;
      }
      $scope.medecin = UserService.getProfil() === "medecin";

      AgentService.get(
        items,
        function (result) {
          if (result.data.dateNaissance) {
            result.data.dateNaissance = createDate(result.data.dateNaissance);
          }
          if (result.data.dateEntree) {
            result.data.dateEntree = createDate(result.data.dateEntree);
          }
          if (result.data.dateSortie) {
            result.data.dateSortie = createDate(result.data.dateSortie);
          }
          if (result.data.dateDerniereVM) {
            result.data.dateDerniereVM = createDate(result.data.dateDerniereVM);
          }
          $scope.agent = result.data;
          if (result.data && result.data.vm.length > 0) {
            $scope.agent.derniereVM = result.data.vm[0].date;
          }
        },
        function () {
          $scope.alerts.push({ type: "danger", msg: "Erreur(s) serveur" });
        }
      );
      $scope.natureVM = [];
      ReferentielService.getAll(
        function (result) {
          for (var i in result.data) {
            var item = result.data[i];
            $scope.natureVM.push({ value: item.valeur, text: item.valeur + " - " + item.info1 });
          }
        },
        null,
        { groupe: "natureVM" }
      );

      //propriété des alert et methode de fermeture
      $scope.alerts = [];
      $scope.closeAlert = function (index) {
        $scope.alerts.splice(index, 1);
      };
      //methode pour le form agent
      $scope.edit;
      $scope.validate;
      $scope.addError;
      //creation des objects modal pouvant être appelé a partir de la fiche agent
      $scope.vmModal = new InstanceVM();
      $scope.openVM = function (id) {
        $scope.vmModal.setItem(id);
        $modalInstance.close();
        $scope.vmModal.open(function (value) {});
      };

      $scope.bsModal = new InstanceBS();
      $scope.openBS = function (id) {
        $scope.bsModal.setItem(id);
        $modalInstance.close();
        $scope.bsModal.open(function (value) {});
      };

      //validation et fermeture de la modal
      $scope.ok = function (agent) {
        if (agent) {
          AgentService.update(
            agent,
            function (result) {
              if (result.status === 200) {
                $scope.alerts.splice(0, $scope.alerts.length);
                $modalInstance.close($scope.agent); //ferme la modal et renvoie l'agent
              } else if (result.status === 207) {
                $scope.alerts.splice(0, $scope.alerts.length);
                $scope.alerts.push({ type: "danger", msg: " erreur : Numéro de matricule déjà existant " });
              } else {
                $scope.alerts.splice(0, $scope.alerts.length);
                $scope.alerts.push({ type: "danger", msg: "Erreur(s) serveur" });
              }
            },
            function () {
              $scope.alerts.splice(0, $scope.alerts.length);
              $scope.alerts.push({ type: "danger", msg: "Erreur(s) serveur" });
            }
          );
        }
      };
      //fermeture de la modal
      $scope.cancel = function () {
        $modalInstance.dismiss();
      };
    },
  ])
  .factory("InstanceAgent", [
    "$modal",
    function ($modal) {
      return function () {
        this.params = {
          templateUrl: "partials/modal/agent.html",
          controller: "AgentCtrl",
          size: "lg",
          backdrop: false,
        };
        this.open = function (action) {
          var agent = $modal.open(this.params);
          agent.result.then(
            function (selectedItem) {
              //close
            },
            function () {
              //console.log('dissim agent');
            }
          );
        };
        this.setItem = function (item) {
          this.params.resolve = {
            items: function () {
              return item;
            },
          };
        };
      };
    },
  ]);
