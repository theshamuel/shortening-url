## SHORTENING-URL

### DESCRIPTION
This is project which provide API for create short URL (similar goo.gl, bit.ly or t.co).

Back-end: Spring (Spring Boot, Spring IoC, Spring Data, Spring Test), JWT library, Java8
DB: MongoDB 3.4
Tools: git,maven,docker.

JDK version 1.8.0_45 is recommended for building jar archive. Build jar to `target` directory, after that if you are ready to release the built jar move it to `release` directiory.

#### Database
The first start of db container import starter dumb of database with collection and demo admin-user for getting JWT token and manage `users and statistics` (admin/admin). The database is ready deploy on production and has all necessary indexes for optimisation in queries. Also for container has scripts for backup on host instanse and to AWS S3. If you need this setup specal enviroment variables.
Important after first start change MONGO_RESTORE variable to false.

There are deploy two containers of mongo `shrturl-db` - main, and `shrturl-db-replica` - for backuping.

#### API
The directory `api/release` has the first version of jar api (the version which will start into docker container is setting up as environment variable (VERSION) in shrturl-api service in docker-compose.yml)
Logs collects to volume which mapped into docker container

#### Monit
If you have a smtp-server you can setup enviromet for monit container which will send you metrics about host instance.

## DEPLOYMENT
0. Download any release
1. Define all nessasery environment variables into docker-compose.xml. 
    - `shrturl-db`  - MongoDB container (Primary)
        - TZ - TimeZone (Ex. Europe/Moscow)
        - MONGO_AUTH - Create user/password for mongod (Ex. true or false)
        - MONGO_SERVER - MongoDB server  
        - MONGO_BACKUP - Create backups to mapped volume (Ex. true or false)
        - MONGO_PORT   - MongoDB server port
        - MONGO_DB=shrturlDB - Name of mongoDB database
        - MONGO_RESTORE - Restore backups to mongoDB or restore starter dump (Ex. true or false)
        - MONGO_ARCHIVE - Name of archive for restoring (Ex. shrturldb_starter.tar.gz)
        - MONGO_ADMIN - Login of admin user by mongoDB
        - MONGO_ADMIN_PASSWORD - Password of admin user by mongoDB
        - MONGO_USER - Login of user by shrturlDB database
        - MONGO_USER_PASSWORD - Password of user by shrturlDB database
        - COPY_TO_S3 - Create backups to AWS S3 bucket (Ex. true or false)
        - AWS_ACCESS_KEY_ID - Security parametr from AWS account for accesing to AWS S3
        - AWS_SECRET_KEY - Security parametr from AWS account for accesing to AWS S3
        - AWS_PATH -  Path into bucket on AWS S3
    - `shrturl-db-replica`  - MongoDB container (Secondary) (backup script of database has supported extra copy to `AWS S3`)
        The enviroment is similar `shrturl-db`. 
    - `shrturl-api` - Java API back-end container (The env variable DOMAIN - it's your dns name of site where your application will be start)
        - VERSION - Version of release api jar. It's should be located in `release` directory (Ex. 0.0.1)
        - DOMAIN - HTTP address to your domain where application has deployed (Ex. `http://mygoo.gl`)
        - MONGO_USER - Login of user by shrturlDB database
        - MONGO_USER_PASSWORD - Password of user by shrturlDB database
    - `shrturl-web` - Nginx-proxy container for routing requests to front-end and API
        - PROXY_SERVER=shrturl-api - Server API
        - PROXY_PORT=9000 - Server API port
    - `monit`  - Monit monitoring CPU, memory, disk space and availability  all containers (if you don't have smtp server you can comments this block)
        - MHOST=127.0.0.1 - Address of server of monitoring
        - MAIL_SERVER - Connect string to SMTP server
        - ALERT_EMAIL - Destination email for alerst
        - MONIT_USER - Login of user for access to web interface of monit
        - MONIT_PASS - Password of user for access to web interface of monit
2. Execute `docker-compose up --build`

## REST API

### Manage users

For getting users you do not have to get JWT by valid User.
0. List of all users in system: `GET: /api/v1/users/`
    - Ex: `curl --request GET \
  --url http://localhost/api/v1/users/ \
  --header 'authorization: Bearer <JWT_TOKEN>' \
  --header 'content-type: application/json'`
  
For creating new user you do not have to get JWT by valid User. The application privide 2 kind of roles [ADMIN,USER].

 1. Create new user in system: `POST: /api/v1/users/`
    - Request body JSON:
        - `{
            "login":<>,
            "password":<>,
            "role":<> [It must be or ADMIN or USER]
        }`
    - Response body JSON:
        - `{
        "id": <>,
	    "createdDate": <>,
	    "modifyDate": <>,
	    "login": <>,
	    "password": <hash_password_SHA256>
        }`
    - Ex: `curl --request POST \
        --url http://localhost/api/v1/users/ \
        --header 'authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImNkYXRlIjp7InllYXIiOjIwMTgsIm1vbnRoIjoiTUFZIiwiZGF5T2ZNb250aCI6MjAsImRheU9mV2VlayI6IlNVTkRBWSIsImRheU9mWWVhciI6MTQwLCJtb250aFZhbHVlIjo1LCJob3VyIjoyLCJtaW51dGUiOjU5LCJzZWNvbmQiOjU5LCJuYW5vIjowLCJjaHJvbm9sb2d5Ijp7ImlkIjoiSVNPIiwiY2FsZW5kYXJUeXBlIjoiaXNvODYwMSJ9fSwiaWF0IjoxNTI2NzEzMzQzLCJleHAiOjE1Mjc5MTMzNDN9.1aArsrGD4oFlK37Veeyj-g_WGYCXHs72hAPvA4jrfPY' \
        --header 'content-type: application/json' \
        --data '{"login":"user","password":"user"}'`

For getting users you have to get JWT by valid User with ADMIN role.  
3. Delete user in system: `DELETE: /api/v1/users/{id}`

### Access
The system work via JWT token authorization. Token expired time is 20 minutes.
1. Get new token: `GET:/auth`
    - BODY JSON:
`{
   "login":<login>,
   "password":<password>
}`
    - Ex: `curl --request POST \
    --url http://localhost/auth \
    --header 'content-type: application/json' \
    --data '{"login":"user","password":"user"}'`

### Manage shorting URLs
For creating new link you do not have to get JWT by valid User.
1. Get new shorted URL: `POST: /api/v1/links`
    - Request body JSON:
        - `{
            "longUrl":<source url>           
        }`
    - Response body JSON:
        - `{
           	"id": <id>,
           	"createdDate": "2018-05-19",
           	"modifyDate": "2018-05-19",
           	"shortUrl": <short URL>,
           	"longUrl": <long url>,
           	"totalClicks": 0
           }`
    - Ex: `curl --request POST \
       --url http://localhost:8081/api/v1/links \
       --data '{
     "longUrl":"https://www.google.com/search?hl=en&sugexp=les;&gs_nf=1&gs_mss=how%20do%20I%20iron%20a%20s&tok=POkeFnEdGVTAw_InGMW-Og&cp=21&gs_id=2j&xhr=t&q=how%20do%20I%20iron%20a%20shirt&pf=p&sclient=psy-ab&oq=how+do+I+iron+a+shirt&gs_l=&pbx=1&bav=on.2,or.r_gc.r_pw.r_cp.r_qf.&biw=1600&bih=775&cad=h"
     }'`
     
### Get links from system
For getting statistics you have to get JWT by valid User with ADMIN role.
 1. Get list of links by user shorted URL: `GET: /api/v1/links`
    - Response body JSON:
        - `	[{
		"id": <>,
		"createdDate": <>,
		"shortUrl": <>,
		"longUrl": <>,
		"totalClicks": <>
	}]`
    - Ex: ``
    

For getting statistics you have to get JWT by valid User with ADMIN role.
 2. Get list of links by user shorted URL: `GET: /api/v1/links/user/{userLogin}`
    - Response body JSON:
        - `	[{
		"id": <>,
		"createdDate": <>,
		"shortUrl": <>,
		"longUrl": <>,
		"totalClicks": <>
	}]`
    - Ex: ``

### Delete link from system
For getting statistics you have to get JWT by valid User with ADMIN role.
 1. Delete list of links by user shorted URL: `DELETE: /api/v1/links/{shortUrl}`
    - Ex: ``
    

### Get statistics
1. Get statistics by particular short URL for period. Dates using in (ISO 8601:2004 - YYYY-MM-DDThh:mm:ss): `GET: /api/v1/statistics/{shortUrl}/{startDate}/{endDate}` 
    - Response body JSON:
        - `[{
        {
		"shortUrl": <>,
		"country": [
			{
				"label": <>,
				"clicks": <>
			}
		],
		"browser": [
			{
				"label": <>,
				"clicks": <>
			}
		],
		"operationSystem": [
			{
				"label": <>,
				"clicks": <>
			}
		]
	    }
        }]`
    - Ex: `curl --request GET \
    --url http://localhost/api/v1/statistics/9mKAtsys/2018-05-01T00:00:00/2018-05-21T00:00:00 \
     --header 'authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImNkYXRlIjp7InllYXIiOjIwMTgsIm1vbnRoIjoiTUFZIiwiZGF5T2ZNb250aCI6MjAsImRheU9mV2VlayI6IlNVTkRBWSIsImRheU9mWWVhciI6MTQwLCJtb250aFZhbHVlIjo1LCJob3VyIjoyLCJtaW51dGUiOjU5LCJzZWNvbmQiOjU5LCJuYW5vIjowLCJjaHJvbm9sb2d5Ijp7ImlkIjoiSVNPIiwiY2FsZW5kYXJUeXBlIjoiaXNvODYwMSJ9fSwiaWF0IjoxNTI2NzEzMzQzLCJleHAiOjE1Mjc5MTMzNDN9.1aArsrGD4oFlK37Veeyj-g_WGYCXHs72hAPvA4jrfPY' \
    --header 'content-type: application/json'`
    
4. Get all statistics for period: `GET: /api/v1/statistics/{startDate}/{endDate}` 
    - Response body JSON:
        - `[{
        {
		"shortUrl": <>,
		"country": [
			{
				"label": <>,
				"clicks": <>
			}
		],
		"browser": [
			{
				"label": <>,
				"clicks": <>
			}
		],
		"operationSystem": [
			{
				"label": <>,
				"clicks": <>
			}
		]
	    }
        }]`
    - Ex: `curl --request GET \
    --url http://localhost/api/v1/statistics/2018-05-01T00:00:00/2018-05-21T00:00:00 \
     --header 'authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImNkYXRlIjp7InllYXIiOjIwMTgsIm1vbnRoIjoiTUFZIiwiZGF5T2ZNb250aCI6MjAsImRheU9mV2VlayI6IlNVTkRBWSIsImRheU9mWWVhciI6MTQwLCJtb250aFZhbHVlIjo1LCJob3VyIjoyLCJtaW51dGUiOjU5LCJzZWNvbmQiOjU5LCJuYW5vIjowLCJjaHJvbm9sb2d5Ijp7ImlkIjoiSVNPIiwiY2FsZW5kYXJUeXBlIjoiaXNvODYwMSJ9fSwiaWF0IjoxNTI2NzEzMzQzLCJleHAiOjE1Mjc5MTMzNDN9.1aArsrGD4oFlK37Veeyj-g_WGYCXHs72hAPvA4jrfPY' \
    --header 'content-type: application/json'`

5. Get all by particular user login URL for period: `GET: /api/v1/statistics/user/{userLogin}/{startDate}/{endDate}` 
    - Response body JSON:
        - `[
        {
		"shortUrl": <>,
		"country": [
			{
				"label": <>,
				"clicks": <>
			}
		],
		"browser": [
			{
				"label": <>,
				"clicks": <>
			}
		],
		"operationSystem": [
			{
				"label": <>,
				"clicks": <>
			}
		]
	    }]`
    - Ex: `curl --request GET \
    --url http://localhost/api/v1/statistics/user/admin/2018-05-01T00:00:00/2018-05-21T00:00:00 \
     --header 'authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImNkYXRlIjp7InllYXIiOjIwMTgsIm1vbnRoIjoiTUFZIiwiZGF5T2ZNb250aCI6MjAsImRheU9mV2VlayI6IlNVTkRBWSIsImRheU9mWWVhciI6MTQwLCJtb250aFZhbHVlIjo1LCJob3VyIjoyLCJtaW51dGUiOjU5LCJzZWNvbmQiOjU5LCJuYW5vIjowLCJjaHJvbm9sb2d5Ijp7ImlkIjoiSVNPIiwiY2FsZW5kYXJUeXBlIjoiaXNvODYwMSJ9fSwiaWF0IjoxNTI2NzEzMzQzLCJleHAiOjE1Mjc5MTMzNDN9.1aArsrGD4oFlK37Veeyj-g_WGYCXHs72hAPvA4jrfPY' \
    --header 'content-type: application/json'`


### Additional description

1. The application provide work with Russian Federation DNS-name (.рф)
2. You can change your DNS-name via env variable DOMAIN into `docker-compose.yml`
3. Web container is ready for deploing front-end part of API, because web server (nginx) start as reverse proxy with particular routes. By default front-end pages will share to container from `shrturl-web/src` directory.
4. Also web server has one simple page which show to you when short link is broken (404.hmtl into `pages`).
