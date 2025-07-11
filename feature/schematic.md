# Feature/Schematic

Concernant la branche <a href="https://github.com/Sandro642/ConnectorAPI/tree/feature/schematic">Feature/Schematic</a>, Une amélioration sera faite du à la compatibilité de chaque utilisateur selon leur schéma utilisé dans leur API.

Voici un exemple de schéma :
```{
  "err": false,
  "code": 200,
  "msg": "MCAS Version",
  "data": {
    "version": "V2-Alpha.0.1.0"
  }
}
```

Pour se faire à cette compatibilité il sera mis à disposition dans le fichier ```infos.yml``` une partie pour créer son propre schéma.

La classe <a href="https://github.com/Sandro642/ConnectorAPI/blob/main/src/main/java/fr/sandro642/github/api/ApiResponse.java">ApiResponse</a>, sera supprimé est remplacé par le nouveau système de schematic.


Voici un exemple de ce que cela va ressembler :

```
# properties Connector API By Sandro642  
  
urlPath: "http://localhost:8080/api"  
  
routes:  
  #info: "/info/version"  
  #ping: "/ping"  
  #status: "/status"


schema:
    #Activer la création de schéma ?
    enable: false


		    #Schéma par défaut: 
			#	msg : string
			#	err: boolean
			#	code: integer
			#	data: Map<String, Object>
							

	#Composants à créer exemple, je vais créer plusieurs composant: 
	#         str::msg
	#         bln::status
	#         int::code
	#         map::data_string:object / string pour une chaine de caractère et       
	#                                 / object pour la récupération de n'importe     
	#                                 / quel type de variable.

	# Grâce à cela vous pourrez les appeler pour récupérer vos propres valeurs       
	# par rapport à votre schéma réponse API
```
