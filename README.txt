Ejemplo de ejecucion Postman

1) login 
https://pruebaservicejava.herokuapp.com/login
body = 
{
	"correo":"gerald@gmail.com",
	"contrasena": "gerald"
}

2) Registro
https://pruebaservicejava.herokuapp.com/ms-desafio/registro
headers
Content-Type:application/json
Authorization:Token <<token>>
body = 
{
        "name": "Juan Rodriguez",
        "email": "jqua1n@rodriguez.org",
        "password": "hunter2",
        "phones": [
            {
                "number": "1234567",
                "citycode": "1",			
                "contrycode": "57"
            }
        ]
}