# Apium Kata

## Tools and Set-up
Rxjava2, dagger2 help in the set-up of the architecture. 
Stetho and Chuck as usefult devtool, build variant debug and release and injecttion are used to set it up correctly

in debug mode, picasso shows color on each image to understant from which cache it comes from:
* red from network
* blue from disk
* green from cach memmory

CI with travis, check if release and debug flavor compile, also look at the Unit test at the moment (Todo: run androidTest for the databases)

## Architecture

The Architecture has 3 layers:
* Data
* Domain
* Presentation

![Alt text](https://github.com/guillaumevalverde/images/blob/master/architecture.png?raw=true "Title")

## DataLayer

Data is the layer which takes care of retrieving the data from network, disk or memory.
there is 

* service: fetch the data through an api
* store: store the data, can be in sharedPref, in Sql database, using contentProvider or in memmory
* repository: combine both service and store, the repo is the link to the domain layer


The Store implemented is using a SQl database with the library Room. this one is used to save the details of Albums and Songs.

## Domain
The ViewModel is the link between the repo and the view. It map the data accordingly to give the right state of the view.

## Presentation
This is all ui related


To download the images we use Picasso which cache both in memory and on disk the data.
It also resize the image accordingly in order not to lose memory.


