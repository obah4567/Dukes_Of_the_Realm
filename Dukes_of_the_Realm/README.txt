
Dukes_Of_the_Realm


Dukes of the Realm est un jeu de strat�gie au tour par tour. Vous arrivez dans un royaume � une �poque moyen�geuse dans une p�riode de grande instabilit� politique o� le roi vient juste de mourir. Vous vous appelez Joueur. Vous �tes un duc, et comme tous les ducs, votre but est de prendre le pouvoir. Ainsi, vous le but du jeu est de s'emparer de tous les ch�teaux du royaume.
Mais attention, si vous perdez tous vos chateaux, vous perdez la partie. 


Instructions


Pour lancer le jeu vous n'avez qu'� compiler et lancer le programme. (ou utiliser la touche Run Main d'Eclipse).



R�gles :



R�gle de victoire : s'emparer de tous les chateaux des ducs : vaincre tous les ennemis.

D�faite : si vous venez � perdre tous vos chateaux.

Pour vos emparer de chateaux, vous recruterez des troupes et les lancerez � l'attaque de vos ennemis. 
Sachez que vos ennemis peuvent aussi vous attaquer, nous vous recommandons donc de toujours garder quelques troupes en garnison.

Tout le jeu se d�roule au cliq, vous n'aurez besoin du clavier que pour indiquer le nombre de troupes que vous souhaitez envoyer � l'attaque, mettre en pause, ou simplement quitter le jeu.



D�roulement du jeu et approfondissement des r�gles: 



La partie commence, tout le monde poss�de un tr�sor nul, un chateau de niveau 1, et quelques troupes, (nombre exacte indiqu� dans Settings.java)
Vos ennemis sont facilement reconaissables, ils poss�dent tous un chateau noir, vous poss�dez un chateau bleu marine. Notez qu'il existe des barons sans ambition particuli�re dont le chateau est blanc et bleu. Ces barons ne souhaitent que vivre tranquillement. Ils ne posent pas de menaces directes, mais vos ennemis peuvent s'en emparer, et utiliser leurs fortunes pour lever des troupes et vous attaquer vous.

La partie se d�roule au tour par tour. Vous pouvez modifier la dur�e d'un tour dans le fichier Settings.java, la variable TIME_TURN, n�anmoins nous vous recommandons de garder une valeur pas trop �lev� de fa�on � ce que le jeu reste amusant.

A chaque tour, chaque ch�teau produit un certain nombre de pi�ces d'or : 10 fois son niveau pour les chateaux de duc et 8 fois son niveau pour les chateaux de barons. 

Dans la partie vous pouvez cliquer sur tous les ch�teaux. Si le ch�teau vous appartient, des options particuli�res devrez appra�tre :
Envoyer des troupes, qui ne vous sert � rien si vous ne poss�dez qu'un seul ch�teau.
Produire une unit�, si vous cliquer dessus, de nouvelles fen�tres vous afficheront les informations de chaque unit�s, et vous pouvez cliquer sur les boutons pour recruter l'unit� que vous d�sirez. Si vous avez assez d'or et que vous n'�tes pas d�j� en train de produire quelque chose, la production se mettra en marche. Sinon une fen�tre vous informera que vous ne pouvez pas effectuer l'op�ration.
Level UP ! : permet de faire monter de niveau au ch�teau. Op�ration possible uniquement si vous avez assez d'or et si vous ne produisez pas quelque chose. Si vous voulez monter au niveau sup�rieur, il vous faut 1000 fois le niveau sup�rieur en pi�ces d'or. Et la production dure 10 fois le niveau.
Si vous avez plusieurs ch�teaux, le cliq sur l'un de vos ch�teau vous donnera une autre option : nouvelle base.
nouvelle base fait en sorte que le chateau devienne votre base. Cela est important car lorsque vous attaquerez un chateau, les troupes que vous enverrez partiront de votre base. 

Si vous cliquez sur un ch�teau ennemi, vous ne pouvez que l'attaquer. Vous s�lectionner le nombre de troupes , vous ne pouvez pas envoyer plus de troupes que vous ne poss�dez. 
Veuillez n'�crire que des chiffres dans les champs pr�vus � cet effet puis cliquer sur clear si vous d�sirez changer ces nombres parce que vous vous �tes tromp�s ou autres... Et ok quand vous �tes sur de vouloir envoyer vos troupes � l'assaut. 

Ainsi un ordre est envoyer(vous ne pouvez envoyer qu'un ordre � la fois : vous pouvez lancer une attaque mais devez attendre la fin de cette derni�re pour lancer une nouvelle attaque) et vos troupes vont se lancer � l'attaque du chateau. 

Elles se d�placeront vers votre cible � chaque tour, lorsqu'elles seront assez proches, elles "se suicideront" en attaquant le chateau ennemi. Chaque troupe attaquant le chateau inflige les d�gats qu'elle peut infliger aux troupes gardant le chateau ennemi et meurt. 
Lorsque le chateau ennemi n'a plus de troupes pour se d�fendre, la prochaine troupe l'attaquant s'empare du chateau.

Vous pouvez �galement appuyer sur la barre d'espace du clavier pour mettre le jeu en pause, ou sur la touche echap pour fermer le jeu sans sauvegarder.

Les ennemis ont des actions al�atoires. Cependant il est possible que certains ennemis aient plus de chances que d'autres avec l'al�atoire. 

Impl�mentation : 

barre d'information d�crivant les statistiques des chateaux. 



Fonctionnement du programme : 



Vous lancez le programme, le main se lance. Le joueur est g�n�r� avec un ch�teau, chaque ennemi et baron neutre est initialis�. Leurs positions sont totalement al�atoires. Une boucle gameLoop se met en marche, cette boucle va mettre � jour � chaque tour, le tr�sor de chaque chateau, la production de chaque chateau etla position des troupes sur la carte s'il y en a. 
De plus le cliq est analys�. 
Tous les �l�ments suffisament proches du cliq sont stock�s dans des ArrayList qui conviennent, puis la fonction handleCompetition fait le tri, et choisit l'objet le plus proche du cliq et le plus probable d'avoir �t� l'objectif de l'auteur du cliq. 

Ainsi s'il s'agit d'un chateau, ce chateau devient le lastCastle. Et jusqu'� ce qu'on clique sur un autre chateau, ses statistiques seront affich�s continuellement dans la barre d'information en bas par la fonction updateStatus(lastCastle)

Si l'on clique sur un autre chateau, ce chateau devient lastCastle...

Si l'on clique � nouveau sur le m�me chateau que lastCastle. Un boolen option prend la valeur true, puis on execute la fonction options en lui passant en param�tre le chateau objet du cliq. 

Selon le propri�taire du chateau, des options sp�cifiques sont affich�s et pr�sent�s au joueur.

Si le joueur clique sur un autre chateau ces options disparaissent, le boolen option et remis � faux, et lastCastle change.

Si le joueur clique sur ces options le booleen option2 prend la valeur vrai, les options qui �taient pr�sent�es sont effac�es, et de nouvelles sont affich�es.
Si le joueur avait cliqu� sur [Attaquer/envoyer des troupes], option2 prend vrai et la fonction optionMenu(optionCliqu�) est appel�. Elle va afficher les 
