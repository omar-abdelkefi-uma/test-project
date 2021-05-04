# Test Project

Il s'agit d'un projet Spring Boot qui contient une API Rest "UserApi", qui expose
deux endpoints REST:

1. "POST: /users/register": Endpoint pour ajouter un nouvel utilisateur.
2. "GET: /users/{userId}": Endpoint pour la récupération d'un utilisateur par son id unique.

## Modèle

Le modèle est simple:

1. Un utilisateur représenté par la classe [User](src/main/java/io/glide/boot/domain/User.java)
appartient à un seul département (classe [Department](src/main/java/io/glide/boot/domain/Department.java))
et peut avoir une ou plusieurs adresses (classe [Address](src/main/java/io/glide/boot/domain/Address.java)
   
2. Plusieurs utlilisateurs peuvent appartenir au meme departement.

## Ce qui est demandé

Le projet en l'état n'est pas fonctionnel:

* Il y'a des erreurs de compilations => il faut les corriger
* Certain test échoue => il faut apporter des corrections pour que ces tests fonctionnent de nouveau
* Il n'y a pas de tests pour la couche API => il faut les écrire

L'objectif étant de faire fonctionner ce projet Spring Boot et avoir des endpoints qui fonctionnent.
La base de donnée à utiliser est une base mémoire (H2 database)


  