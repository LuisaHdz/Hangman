Este juego utiliza la pagina:
https://www.serverbpw.com/cm/2024-2/hangman.php
Con la que se obtiene una respuesta en formato JSON con los siguientes datos:
• word: palabra a adivinar
• category: categoría de la palabra (para presentársela al usuario y le sirva como pista para saber de qué tema es la palabra).

Al iniciar el juego se muestra un SplashScreen que contiene:
• Nombre del juego
• Logo
• Creador
• Version

El juego:
-Unicamente acepta letras [A-Z][a-z], no permite ingresar otro caracter.
-Solo activa el boton "Verificar" cuando se tiene un caracter en el EditText
-Valida si la letra ya fue utilizada anteriormente y muestra un Toast si es asi, no se castiga por volver a utilizarla
-Muestra el muñeco del logo al cual se le van quitando partes cada que se comete un error (6 errores en total)
-Cuenta con layouts para cuando se gana o se pierde, en los cuales se tiene la opcion de volver a jugar o salir del juego
-Muestra cual era la respuesta correcta en el layout de Perdedor.
-Muestra un layout cuando no hay conexion en el cual se da la opcion de volver a intentar entrar al juego o salir.
