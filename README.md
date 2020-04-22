# Don't forget to sleep

Ce projet est une application web qui permet de gérer sa liste de tâches à accomplir.


Pour lancer le projet, utilisez la commande suivante :  
docker run -p 80:8080 -e "MDP_SMTP=VOTRE PASSWORD" jojoc4/dont-forget-to-sleep-jee

Pour exécuter en local, commentez la ligne   
spring.mail.password=${MDP_SMTP}  
Et décommentez la ligne  
spring.mail.password=1234
