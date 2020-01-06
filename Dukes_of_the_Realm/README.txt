
Dukes_Of_the_Realm


Dukes of the Realm est un jeu de stratégie au tour par tour. Vous arrivez dans un royaume à une époque moyenâgeuse dans une période de grande instabilité politique où le roi vient juste de mourir. Vous vous appelez Joueur. Vous êtes un duc, et comme tous les ducs, votre but est de prendre le pouvoir. Ainsi, vous le but du jeu est de s'emparer de tous les châteaux du royaume.
Mais attention, si vous perdez tous vos chateaux, vous perdez la partie. 


Instructions


Pour lancer le jeu vous n'avez qu'à compiler et lancer le programme. (ou utiliser la touche Run Main d'Eclipse).



Règles :



Règle de victoire : s'emparer de tous les chateaux des ducs : vaincre tous les ennemis.

Défaite : si vous venez à perdre tous vos chateaux.

Pour vos emparer de chateaux, vous recruterez des troupes et les lancerez à l'attaque de vos ennemis. 
Sachez que vos ennemis peuvent aussi vous attaquer, nous vous recommandons donc de toujours garder quelques troupes en garnison.

Tout le jeu se déroule au cliq, vous n'aurez besoin du clavier que pour indiquer le nombre de troupes que vous souhaitez envoyer à l'attaque, mettre en pause, ou simplement quitter le jeu.



Déroulement du jeu et approfondissement des règles: 



La partie commence, tout le monde possède un trésor nul, un chateau de niveau 1, et quelques troupes, (nombre exacte indiqué dans Settings.java)
Vos ennemis sont facilement reconaissables, ils possèdent tous un chateau noir, vous possédez un chateau bleu marine. Notez qu'il existe des barons sans ambition particulière dont le chateau est blanc et bleu. Ces barons ne souhaitent que vivre tranquillement. Ils ne posent pas de menaces directes, mais vos ennemis peuvent s'en emparer, et utiliser leurs fortunes pour lever des troupes et vous attaquer vous.

La partie se déroule au tour par tour. Vous pouvez modifier la durée d'un tour dans le fichier Settings.java, la variable TIME_TURN, néanmoins nous vous recommandons de garder une valeur pas trop élevé de façon à ce que le jeu reste amusant.

A chaque tour, chaque château produit un certain nombre de pièces d'or : 10 fois son niveau pour les chateaux de duc et 8 fois son niveau pour les chateaux de barons. 

Dans la partie vous pouvez cliquer sur tous les châteaux. Si le château vous appartient, des options particulières devrez appraître :
Envoyer des troupes, qui ne vous sert à rien si vous ne possédez qu'un seul château.
Produire une unité, si vous cliquer dessus, de nouvelles fenêtres vous afficheront les informations de chaque unités, et vous pouvez cliquer sur les boutons pour recruter l'unité que vous désirez. Si vous avez assez d'or et que vous n'êtes pas déjà en train de produire quelque chose, la production se mettra en marche. Sinon une fenêtre vous informera que vous ne pouvez pas effectuer l'opération.
Level UP ! : permet de faire monter de niveau au château. Opération possible uniquement si vous avez assez d'or et si vous ne produisez pas quelque chose. Si vous voulez monter au niveau supérieur, il vous faut 1000 fois le niveau supérieur en pièces d'or. Et la production dure 10 fois le niveau.
Si vous avez plusieurs châteaux, le cliq sur l'un de vos château vous donnera une autre option : nouvelle base.
nouvelle base fait en sorte que le chateau devienne votre base. Cela est important car lorsque vous attaquerez un chateau, les troupes que vous enverrez partiront de votre base. 

Si vous cliquez sur un château ennemi, vous ne pouvez que l'attaquer. Vous sélectionner le nombre de troupes , vous ne pouvez pas envoyer plus de troupes que vous ne possédez. 
Veuillez n'écrire que des chiffres dans les champs prévus à cet effet puis cliquer sur clear si vous désirez changer ces nombres parce que vous vous êtes trompés ou autres... Et ok quand vous êtes sur de vouloir envoyer vos troupes à l'assaut. 

Ainsi un ordre est envoyer(vous ne pouvez envoyer qu'un ordre à la fois : vous pouvez lancer une attaque mais devez attendre la fin de cette dernière pour lancer une nouvelle attaque) et vos troupes vont se lancer à l'attaque du chateau. 

Elles se déplaceront vers votre cible à chaque tour, lorsqu'elles seront assez proches, elles "se suicideront" en attaquant le chateau ennemi. Chaque troupe attaquant le chateau inflige les dégats qu'elle peut infliger aux troupes gardant le chateau ennemi et meurt. 
Lorsque le chateau ennemi n'a plus de troupes pour se défendre, la prochaine troupe l'attaquant s'empare du chateau.

Vous pouvez également appuyer sur la barre d'espace du clavier pour mettre le jeu en pause, ou sur la touche echap pour fermer le jeu sans sauvegarder.

Les ennemis ont des actions aléatoires. Cependant il est possible que certains ennemis aient plus de chances que d'autres avec l'aléatoire. 

Implémentation : 

barre d'information décrivant les statistiques des chateaux. 



Fonctionnement du programme : 



Vous lancez le programme, le main se lance. Le joueur est généré avec un château, chaque ennemi et baron neutre est initialisé. Leurs positions sont totalement aléatoires. Une boucle gameLoop se met en marche, cette boucle va mettre à jour à chaque tour, le trésor de chaque chateau, la production de chaque chateau etla position des troupes sur la carte s'il y en a. 
De plus le cliq est analysé. 
Tous les éléments suffisament proches du cliq sont stockés dans des ArrayList qui conviennent, puis la fonction handleCompetition fait le tri, et choisit l'objet le plus proche du cliq et le plus probable d'avoir été l'objectif de l'auteur du cliq. 

Ainsi s'il s'agit d'un chateau, ce chateau devient le lastCastle. Et jusqu'à ce qu'on clique sur un autre chateau, ses statistiques seront affichés continuellement dans la barre d'information en bas par la fonction updateStatus(lastCastle)

Si l'on clique sur un autre chateau, ce chateau devient lastCastle...

Si l'on clique à nouveau sur le même chateau que lastCastle. Un boolen option prend la valeur true, puis on execute la fonction options en lui passant en paramètre le chateau objet du cliq. 

Selon le propriétaire du chateau, des options spécifiques sont affichés et présentés au joueur.

Si le joueur clique sur un autre chateau ces options disparaissent, le boolen option et remis à faux, et lastCastle change.

Si le joueur clique sur ces options le booleen option2 prend la valeur vrai, les options qui étaient présentées sont effacées, et de nouvelles sont affichées.
Si le joueur avait cliqué sur [Attaquer/envoyer des troupes], option2 prend vrai et la fonction optionMenu(optionCliqué) est appelé. Elle va afficher les 
