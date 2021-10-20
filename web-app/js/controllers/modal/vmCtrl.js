/* * Copyright (C) 2014 thurgi*/
angular
  .module("VM", ["ui.bootstrap"])
  .controller("VMCtrl", [
    "$scope",
    "$modalInstance",
    "items",
    "VmService",
    "ReferentielService",
    "BsService",
    "UserService",
    "RemarquesService",
    "Calendrier",
    function (
      $scope,
      $modalInstance,
      items,
      VmService,
      ReferentielService,
      BsService,
      UserService,
      RemarquesService,
      Calendrier
    ) {
      $scope.natures = [];
      $scope.typeBS = [];
      $scope.medecins = [];
      $scope.refMedecin = "";
      $scope.typeBsSelected = [];
      $scope.bs = "false";
      $scope.alertTypeBs = false;
      $scope.remarques = false;

      $scope.calendrierDerniereVm = new Calendrier();

      //autorisation
      $scope.medecin = UserService.getProfil() === "medecin";
      $scope.sp = UserService.getProfil() === "SP";

      $scope.actionBs = function (bs) {
        if (bs === "true") {
          //création d'un bs lié à la vm
          var types = "";
          for (var key in $scope.typeBsSelected) {
            types = types + ($scope.typeBsSelected[key] ? "_" + key : "");
          }
          types = types.substring(1);
          if (types.length > 1) {
            $scope.alertTypeBs = false;
            var post = {
              type: types,
              remarques: $scope.vm.remarques,
              idVm: items,
            };
            BsService.add(
              post,
              function (result) {
                $scope.vm.bs = result.data.id;
                $scope.remarques = true;
                $scope.addError("info", "Bs correctement enregistré");
              },
              function (result) {
                if (result.status === 409) {
                  $scope.addError("danger", "Le bilan sanguin a déjà été créé");
                  return;
                }
                $scope.addError("danger", "Erreur(s) du service bilan sanguin");
              }
            );
          } else {
            $scope.alertTypeBs = true;
          }
        } else {
          //suppression du bs lié
          if ($scope.vm.bs) {
            BsService.remove(
              $scope.vm.bs,
              function (result) {
                $scope.remarques = false;
                for (var key in $scope.typeBsSelected) {
                  $scope.typeBsSelected[key] = false;
                }
                $scope.vm.remarques.splice(0, $scope.vm.remarques.length);
                $scope.addError("info", "Bs correctement supprimé");
              },
              function () {
                $scope.addError("danger", "Erreur(s) du service bilan sanguin");
              }
            );
          } else {
            $scope.vm.remarques.splice(0, $scope.vm.remarques.length);
            $scope.addError("danger", "Bilan sanguin non prescrit");
          }
        }
      };

      //récupération de la vm
      VmService.get(items, function (result) {
        $scope.vm = result.data;

        if (!_.find($scope.natures, { value: result.data.natureVM })) {
          $scope.natures.push({ value: result.data.natureVM, text: result.data.natureVM });
        }

        $scope.bs = result.data.bs ? "true" : "false";
        $scope.remarques = $scope.bs;
        ReferentielService.getAll(
          function (result) {
            for (var i in result.data) {
              $scope.typeBS.push(result.data[i].valeur);
            }
          },
          null,
          { groupe: "typeBS" }
        );
        if (result.data.typeBs) {
          var types = result.data.typeBs.split("_");
          for (var i in types) {
            $scope.typeBsSelected[types[i]] = true;
          }
        }
        $scope.btnGenerer =
          ["affecté", "convoqué", "effectué", "cloturé"].indexOf(result.data.statut) !== -1 &&
          UserService.getProfil() === "medecin";
        //effectué - cloturé
        if ((result.data.statut === "effectué" || result.data.statut === "cloturé") && $scope.medecin) {
          $scope.medecin = false;
          $scope.sp = true;
        }
      });

      ReferentielService.getAll(
        function (result) {
          $scope.vusPar = _.map(result.data, function (item) {
            return item.valeur;
          });
        },
        null,
        { groupe: "vuparVM" }
      );

      ReferentielService.getAll(
        function (result) {
          for (var i in result.data) {
            var item = result.data[i];
            $scope.natures.push({ value: item.valeur, text: item.valeur + " - " + item.info1 });
          }
        },
        null,
        { groupe: "natureVM" }
      );

      ReferentielService.getAll(
        function (result) {
          for (var i in result.data) {
            $scope.medecins.push(result.data[i].valeur);
          }
        },
        null,
        { groupe: "medecin" }
      );

      $scope.reload = function () {
        VmService.get(items, function (result) {
          result.data.refMedecin = parseInt(result.data.refMedecin);
          $scope.vm = result.data;
          if (!_.find($scope.natures, { value: result.data.natureVM })) {
            $scope.natures.push({ value: result.data.natureVM, text: result.data.natureVM });
          }

          $scope.bs = result.data.bs ? true : false;
        });
      };
      // alerts
      $scope.alerts = [];
      $scope.addError = function (t, txt) {
        $scope.alerts.splice(0, $scope.alerts.length);
        $scope.alerts.push({ type: t, msg: txt });
      };
      $scope.closeAlert = function (index) {
        $scope.alerts.splice(index, 1);
      };
      //email
      $scope.emailAgent = function () {
        saveVM(
          function () {
            VmService.courriel(
              "mailAgent",
              { id: $scope.vm.id },
              function (result) {
                if (result.status === 200) {
                  $scope.vm.dateEnvoisCourrierAgent = result.data.dateEnvoisCourrierAgent;
                  $scope.addError("info", "Mail récapitulatif envoyé à l'agent");
                } else {
                  $scope.addError("danger", "Erreur(s) serveur : impossible d'envoyer l'email");
                }
              },
              function () {
                $scope.addError("danger", "Erreur(s) serveur");
              }
            );
          },
          function () {
            $scope.addError("danger", "Erreur(s) serveur");
          }
        );
      };
      $scope.emailRuRrh = function () {
        saveVM(
          function () {
            VmService.courriel(
              "emailRuRrh",
              { id: $scope.vm.id },
              function (result) {
                if (result.status === 200) {
                  $scope.addError("info", "Mail récapitulatif envoyé au DU et RRH");
                  $scope.vm.dateEnvoisCourrierDURRH = result.data.dateEnvoisCourrierDURRH;
                } else {
                  $scope.addError("danger", "Erreur(s) serveur : impossible d'envoyer l'email");
                }
              },
              function () {
                $scope.addError("danger", "Erreur(s) serveur");
              }
            );
          },
          function () {
            $scope.addError("danger", "Erreur(s) serveur");
          }
        );
      };

      function saveVM(handleSuccess, handleError) {
        //enregistrement des observations en cours d'édition
        var serviceObservation = RemarquesService("Observation");
        var serviceConclusion = RemarquesService("Conclusion");
        var serviceRemarque = RemarquesService("Remarque");
        for (var i in $scope.vm.observations) {
          if ($scope.vm.observations[i].edit) {
            serviceObservation.update({ id: $scope.vm.observations[i].id, text: $scope.vm.observations[i].text });
          }
        }
        for (var i in $scope.vm.conclusions) {
          if ($scope.vm.conclusions[i].edit) {
            serviceConclusion.update({ id: $scope.vm.conclusions[i].id, text: $scope.vm.conclusions[i].text });
          }
        }
        for (var i in $scope.vm.remarques) {
          if ($scope.vm.remarques[i].edit) {
            serviceRemarque.update({ id: $scope.vm.remarques[i].id, text: $scope.vm.remarques[i].text });
          }
        }
        //enregistrement de la visite médical
        VmService.update($scope.vm, handleSuccess, handleError);
      }
      //fermeture
      $scope.ok = function () {
        saveVM(
          function () {
            $modalInstance.close();
          },
          function () {
            $scope.addError("danger", "Erreur(s) serveur");
          }
        );
      };
      $scope.cancel = function () {
        $modalInstance.dismiss();
      };
    },
  ])
  .factory("InstanceVM", [
    "$modal",
    function ($modal) {
      return function () {
        this.params = {
          templateUrl: "partials/modal/vm.html",
          controller: "VMCtrl",
          size: "lg",
          backdrop: false,
        };
        this.open = function (action) {
          var vm = $modal.open(this.params);
          vm.result.then(
            function (selectedItem) {
              action(selectedItem);
            },
            function () {}
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
