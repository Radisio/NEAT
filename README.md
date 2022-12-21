# Projet génétique Paque Eric Ugo MASI2 (2022-2023) : Partie 2 - Neat
## Introduction
  Continuation direct du premier projet, ce document contiendra exclusivement des informations concernant mon implémentation de l'algorithme NEAT et les 
  quelques modifications apportées au projet précédent afin de le faire fonctionner. Vous retrouverez toutes les informations concernant les éléments de la 
  première partie tel que la description du jeu, de la créature et tout ce qui concerne l'environnement (y compris sa génération) dans ce repository : https://github.com/Radisio/GAFirstProjectCreature
## Approche adopté pour le projet
 Contrairement au premier projet, j'ai décidé d'adopter une approche agile en développant une partie à la fois et en la testant à chaque fois. Agissant ainsi, il a été
 bien plus facile pour moi de détecter d'éventuels bugs et conception hasardeuse de certaines parties assez tôt et de manière locale, évitant ainsi de devoir débugger tout
 le code, même si j'ai du le réaliser pour la partie finale mais j'y reviendrais.
## Organisation générale du code
 L'accent a été mis sur la généricité, ou du moins faciliter l'export de l'algorithme NEAT à d'autres problèmes que le XOR. J'ai réalisé cela en concevant, principalement, 
 une classe abstraite : "FitnessComputation" qui se charge du calcul du fitness et une interface "FitnessComparison" chargé de la comparaison de deux fitness ainsi que de 
 leur tri.
 Les deux implémentations, XOR et GAME, ont chacun une classe dérivant de ces deux interfaces/classes abstraites. 
 Cela a permit de conserver l'algorithme NEAT inchangé pour les deux implémentations.
 
 La partie comprenant le Game a été repris du projet précédent. Deux méthodes ont été ajoutée dans la classe Game : "startTrainNeatDisplay" et "startTrainNeat". Ces 
 derniers prennent en paramètres un réseau neuronal et ce dernier, à chaque itération, va prédire un mouvement.
 Une classe GameNeat a également été développé, elle contient une méthode "gameToNNInput" qui permet de convertir le jeu en entrée neuronale (une entrée par case), deux 
 entrées pour la position du joueur et deux entrées pour la position du point d'arrivée. Chaque entrées non-utilisé (car environnement plus petit) est mise à 1, comme
 une case occupée.
 
## Les tests
  Comme expliqué précédemment, des tests ont été réalisé pour tester différentes parties de l'algorithme génétique. Ces différents tests ont été réalisé à la suite du 
  développement de chacune des parties. Ces tests sont pour la plupart unitaire, cependant je pense que l'on pourrait considérer certains d'entre-eux comme des tests d'intégration
  car pour pouvoir tester certaines fonctionnalités il faut que des précédentes soient déjà fonctionnelle.
 
## Auto-critique
  Au vu du temps disponible, je suis plutôt satisfait. Cependant, je suis tout à fait conscient, et décu, de l'écriture par moment de mon code. L'optimisation n'est également
  pas très présente étant donné que mon objectif premier était d'avoir un algorithme fonctionnelle (J'y ai tout de même réfléchi et certains choix témoignent de ma volonté
  tout de même d'avoir un code "optimisé", mais je suis conscient d'être assez loin du compte).
  De plus, bon nombre d'endroits dans le code mériterait d'être refactorisé en utilisant des design patterns approprié, à commencer par le constructeur de ma classe 
  "GeneticAlgorithm" qui est tout bonnement trop long.
  Je n'ai pas implémenté de parallélisation dans le code car je n'ai tout simplement pas eu le temps de le faire.
  
 ## Difficultés rencontrées
  J'ai rencontré un certain nombre de soucis tout de même:
  - Les tests retournant une valeur à virgule flottante. Réalisant mes calculs sur une calculatrice ou bien sur excel, un programme arrondissant, un autre pas, il était 
  parfois un peu compliqué de savoir si la valeur renvoyée était bonne ou non. De plus, certain tests exécutée plusieurs fois retournait tantot une valeur arrondi, tantot une valeur 
  avec plus de décimale, rendant alors un test fixe plus compliqué.
  - Le XOR m'a posé beaucoup de soucis. En effet, en utilisant une double sigmoide comme fonction d'activation (une sur la couche cachée, une pour la sortie), l'algorithme
  reste coincée dans un minimum local de 3 (meilleur fitness de 4) indéfiniment, donnant donc lieu à un réseau ayant raison 3 fois sur 4, systématiquement. L'implémentation
  d'une fonction d'activation gaussienne en sortie m'a permit de résoudre ce problème. Note: Le jeu cependant a été rapide à implémenter une fois le XOR terminé (de l'ordre de 1 à 2 heures environ).
 
 ## Retour
  A l'exception du manque de temps, j'ai personnellement trouvé le projet enrichissant et divertissant. L'algorithmie génétique m'était entière inconnue avant cette année
  et ces projets, surtout celui-ci, m'ont permit d'en apprendre énormément.
  
## Source
- Cours
- https://nn.cs.utexas.edu/downloads/papers/stanley.ec02.pdf
- https://www.youtube.com/@neatai6702
- https://www.cs.ucf.edu/~kstanley/neat.html
