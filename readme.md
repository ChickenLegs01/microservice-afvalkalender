# Afvalkalender

## Purpose
Service my ardunio will call to know what light to turn on

## Endpoints
* GET http://<HOST>:<PORT>/whichafval
    * Get which light should be on
* GET http://<HOST>:<PORT>/actuator/info
    * Get real service status (ie is afvalkalender available
* GET http://<HOST>:<PORT>/addressdata
    * Get afvalkalender address data

* GET http://<HOST>:<PORT>/lastophaaldata
    * Get last data received from afval service