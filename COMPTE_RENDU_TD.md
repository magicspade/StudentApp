Compte rendu TD 02
===================

L'application s'utilise de manière intuitive grâce à l'interface graphique qui permet pour le moment de créer, modifier et supprimer des étudiants. La fonction findById de l'EJB est utilisé pour sélectionner l'étudiant à modifier/supprimer, la fonction find est quant à elle utilisée pour afficher le tableau des étudiants.

N.B : A l'heure actuelle, l'injection de l'EJB fait planter le serveur (erreur 500). Je cherche activement l'origine de cette erreur mais je n'ai pas encore réussi à la résoudre. Les tests de l'UI sont donc fait avec des objets codés en dur, néanmoins, toutes les lignes devant normalement faire intervenir les ejb ont été écrites et sont commenté avec le // en tout début de ligne.

----------

Question 1
-------------
>  En quoi, le type de Session Bean utilis ́e dans le projet est adaptée aux besoins du projet ?

Le Bean utilisé est ici un stateless, en effet les actions utilisés sont des actions simples pour lesquelles l'historiques des actions exécutées n'est pas nécessaire. En effet pour créer un nouvel étudiant par exemple, on n'a que faire de savoir si d'autres ont déjà été créés, supprimés, etc. Stateless est donc le type de Bean le plus adapté et donnant la meilleure réactivité. 

Question 2
-------------
>  Quelle est l’utilitée de proposer l’interface remote dans un fichier jar separ ́e de l’application ?

Nous développons ici une application web qui pourra peut-être à l'avenir, travailler avec d'autre machines. L'interface remote permet à notre application de fonctionner avec des clients tournant sur une autre JVM.

Question 3
-------------
>  Si vous deviez exporter votre projet vers un serveur EE dans une machine distante, quels fichiers devraient être téléchargés sur la machine distante ?

L'EJB lui même doit être placé dans une archive JAR qui contient :
	* Toutes les classes et leurs fichier de description (persistence.xml)
	* un fichier de configuration ejb-jar.xml dans un sous répertoire /META-INF

Notre servlet Vaadin quant à lui doit être placé dans une archive WAR qui se comporte comme une archive JAR mais contient en plus le fichier web.xml dans le dossier /WEB-INF

Ces deux archives peuvent être assemblées dans une archive EAR qui se comporte comme une archive JAR mais contient en plus un fichier application.xml dans le répertoire /META-INF

Ainsi ont distingue bien deux éléments de la structure MVC : l'archive WAR contient la Vue et le Controleur (mélangés par vaadin), l'archive JAR contient un Modèle.

Question 4
-------------
>  Est-ce possible d’exécuter l’application depuis une machine différente de celle où se trouve l’application EE ? Quelles sont les caractéristiques de l’application (en terme de type de EJB et ressources utilisées pour la persistance ) qui motivent votre réponse ?

Oui il est possible d'exécuter l'application depuis une machine différente et c'est même le but des application Web. Tout d'abord le fait d'utiliser un Bean stateless, nous pousse à croire que n'importe quels client peut utilisé l'application indépendamment des autres et de son passé avec celle-ci. De plus l'interface remote de ce Bean permet en particulier d'y accéder depuis une machine différente. Ensuite le fait d'utiliser des données persistante, c'est à dire stockées dans une base de donnée, confirme la volonté d'utiliser l'application depuis une autre machine : on peut créer un objet, changer de machine, intervenir à nouveau sur cet objet, etc car ces objets sont persistant. 

