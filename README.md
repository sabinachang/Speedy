# Speedy
An Android app that helps English learner to create flashcards quickly. 

# Features
Speedy contains two main sections: "Create Flashcards" and "Review Flashcards".

* Create Flashcards includes the following features:

  * Detection of text in a photo
     Optical Character Recognitin (OCR) is a techonology that can convert documents or words in a photo into editable text.     Speedy uses OCR service provided by Firebase ML Kit Cloud Vision API. The API takes a photo of document as the input and     returns the detected text in JSON.  
   
  * Simple image preprocessing
    In order to have good detection accuracy, photos being detected should be in portrait mode and without any significant      skewing. Thus Speedy integrates third-party library uCrop to provide additional photo processing once an image is taken.      You can scale and rotate the photo to your likings before submittiing the photo for OCR detection.
   
  * Clickable text displayed on custom View
    The detected document body is first drawn line by line onto a custom View. Each line of text is also parsed into its        constituent words. You can long-pressed on any new vocabulary to see its meaning. 
   
  * Definition lookup 
    Speedy provides built-in dictionary by connecting to Wordnik API. After a new vocabulary is being selected, you can hit     the search button to view definition.
 
  * Fast Flashcard creation
    If you would like to convert a new vocabulary and its definition into Flashcard, simply hit the add button. Speedy saves   the Flashcard under a stack that you assigned.
  
* Review Flashcards includes the following features:

  * Easy review of new vocabularies
    Speedy categorized all Flashcards under different stacks. So you can easily go back to study the vocabularies and amp up   your vocabulary game :)

# Screenshots
<img src="https://github.com/sabinachang/Speedy/blob/master/ScreenShots/44166618_477544389318038_5669249825904787456_n.jpg" width="30%"> <img src="https://github.com/sabinachang/Speedy/blob/master/ScreenShots/IMG_20181031_171227.png" width="30%"> 

<img src="https://github.com/sabinachang/Speedy/blob/master/ScreenShots/IMG_20181031_171242.png" width="30%"> <img src="https://github.com/sabinachang/Speedy/blob/master/ScreenShots/IMG_20181031_171301.png" width="30%"> < <img src="https://github.com/sabinachang/Speedy/blob/master/ScreenShots/IMG_20181031_171155.png" width="30%">


    
# Requirements
* Android Studio 3.1+
* Android SDK 23+
* Gradle 3.1+

# Contacts
En-Han Chang
sabchang1@gmail.com
  
  

  
  
  
  
