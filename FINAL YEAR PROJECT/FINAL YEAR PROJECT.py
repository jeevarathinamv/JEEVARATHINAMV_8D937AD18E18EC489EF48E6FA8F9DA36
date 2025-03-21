print("*********************** WELCOME JR ONLINE FOOD ORDERING RESTAURANT*********************")
print("NUMBER ONE OPEN BOX FOOD DELIVERY ")
import time
print(time.strftime("RESTAURANT TIME %H:%M"))
alarm_time = input("ENTER THE 24 CLOCK hOURS CURRENT ORDER TIME(HH:MM): ")
while time.strftime("%H:%M") != alarm_time:
    time.sleep(1)
print("DEAR CUSTOMER ROCK,PAPER,SCISSORS GAME YOU WILL PLAY AND WIN!")
print("YOUR WIN 25% LESS THE FOOD AMOUNT!")

import random
choices = ['rock', 'paper' , 'scissors']
restaurant_choice = random.choice(choices)
customer_choice = input("CHOOSE YOUR WIN DISCOUNT COUPONS NUMBER :ROCK, PAPER, OR SCISSORS: ").lower()

if customer_choice in choices:
    if customer_choice == restaurant_choice:
        print("IT'S A TIE!")
        print("OK FINE ORDER THE FOOD")
    elif (
        (customer_choice == 'rock'and restaurant_choice == 'scissors') or
        (customer_choice == 'paper' and restaurant_choice == 'rock') or
        (customer_choice == 'scissors' and restaurant_choice == 'paper')
    ):
        print("YOUR WIN FOOD COUPON NUMBER 98943961!")
        print("OK FINE YOUR WIN ORDER THE FOOD NUMBER IS NOTED")
    else:
        print("YOU LOSS THE FOOD COUPON NUMBER SORRY CUSTOMER!")
        print("OK FINE ORDER THE FOOD")
else:
    print("INVALID CHOICE.")

NAME= input("ENTER CUSTOMER NAME:")
PHONE = input("ENTER PHONE NUMBER:")
LOCATION= input("CURRENT ORDER LOCATION:")
import webbrowser
url = "file:///E:/html/index.html"
webbrowser.open(url)

class Restaurant:
    def __init__(self, foods):
        self.foods = foods

    def list_foods(self):
        print("AVAILABLE FOODS")
        for food in self.foods:
            print(food)

    def order_food(self, order_food):
        if order_food in self.foods:
            print("WAIT YOUR FOODS NOW OR YOU WILL GET A CALL FROM THE HOTEL")
            self.foods.remove(order_food)
        else:
            print("FOOD NOT AVAILABLE")


    def Location_food(self, Location_food):
        name=input("ENTER YOUR NAME:")
        CUPANNUMBER=int(input("ENTER YOUR CUPAN NUMBER:"))
        if CUPANNUMBER >=9998999999:
             print(name,"CUPAN NUMBER is",CUPANNUMBER,"YOU CAN ALLOW FOR CUPAN NUMBER ")
        else:
              print(name,"CUPAN NUMBER",CUPANNUMBER,"YOU CAN NOT ALLOW FOR CUPAN NUMBER ")
              print("YOU HAVE LOCATION THE FOOD AMOUNT IS CASH ON DELIVERY THANK YOU COME AGAIN")
        self.foods.append(Location_food)
              
foods = ['KELVARAGU(RAGI)DOSA','KAMBU DOSA','CHAPATI' ,'VEG BURGER' ,'VEG DUM BIRYANI','VEG PULAO',
         'PULIYODHARAI RICE','DOSA SAMBAR','IDLI (5)WITH SMBAR','TOMATO RICE','CHICKEN BIRYANI',
         'BIRYANI FAMILY BACK','CHICKEN 65','CHICKEN RICE','CHICKEN PULO','CHICKEN BURGER','BUTTER CHICKEN'
        'WEDDING CATERING','ENGAGEMENT PARTIES','BIRTHDAY PARTIES CATERING','PRIVATE FUNCTION CATERING',
         'OFFICE EVENTS & PARTIES CATERING','PRIVATE EVENTS','SOCIAL EVENTS CATERING','BABY SHOWER','CONCESSION GATHERING CATERING']
o = Restaurant(foods)
print("JR ONLINE FOOD ORDERING RESTAURANT VEG MENU:")
names ={'KELVARAGU(RAGI)DOSA $20',
        'KAMBU DOSA…………………………$20',
        'CHAPATI……………………………..$20',
        'VEG BURGER………………………$139',
        'VEG DUM BIRYANI……………$499',
        'VEG PULAO……………………………$200',
        'PULIYODHARAI RICE………$49',
        'DOSA SAMBAR………………………$49',
        'IDLI (5)WITH SMBAR……$50',
        'TOMATO RICE………………………$49'}
for name in names:
    print(name)

print("JR ONLINE FOOD ORDERING RESTAURANT NON-VEG MENU:")
names ={'CHICKEN BIRYANI..... $499',
        'BIRYANI FAMILY BACK..$999',
        'CHICKEN 65...........&249',
        'CHICKEN RICE.........$199',
        'CHICKEN PULO.........$149',
        'CHICKEN BURGER.......$99',
        'BUTTER CHICKEN.......$199'}
for name in names:
    print(name)

print("ADVANCE FOOD ORDER (CHOOSE YOUR FUNCTION NAME):")
names={'WEDDING CATERING',
       'ENGAGEMENT PARTIES',
       'BIRTHDAY PARTIES CATERING',
       'PRIVATE FUNCTION CATERING' ,
       'OFFICE EVENTS & PARTIES CATERING',
       'PRIVATE EVENTS',
       'SOCIAL EVENTS CATERING',
       'BABY SHOWER',
       'CONCESSION GATHERING CATERING'}
for name in names:
    print(name)
    
msg = """
      1.DISPLAY FOOD LIST
      2.ORDER FOOD NAME OR ADVANCE FOOD ORDER 
      3.REGULAR CUSTOMER OR RESTAURANTN WORKER
      4.CONFORM YOUR DELIVERY
      5.RETURNS ALLOWED
"""

while True:
    print(msg)
    ch = int(input("ENTER YOUR CHOICE : "))
    if ch == 1:
        o.list_foods()
    elif ch == 2:
        food = input("ENTER FOOD NAME TO ORDER OR CHOOSE YOUR FUNCTION NAME: ")
        o.order_food(food)
    elif ch == 3:
        food = input("CUSTOMER OR WORKER PLAY THE GAME YOUR WIN 25% FOOD AMOUNT DSCOUNT PRESS ENTER: ")
        print("THE GAME NAME IS GUESS THE NUMBER")
        import random
        random_number =random.randint(1 ,100)
        attempts = 0
        while True:
            guess = int(input("Guess the number: "))
            attempts += 1

            if guess == random_number:
                 print(f"congratulation! you guessed it in {attempts} attempts.")
                 print("YOUR WIN FIRST FIVE CUPAN NUMBER 99989")
                 break
            elif guess < random_number:
                print("try higher.")

            else:
                print("try lower.")
        food = input("PRESS ENTER REGULAR CUSTOMER or RESTAURANTN  WORKER : ")
                   
        o.Location_food(food)
    elif ch == 5:
        food = input("ENTER YOUR REASON :('FOOD QUALITY IS NOT GOOD or MISMATCH FOOD or DAMAGED FOOD BOX')  : ")
        food = input("ENTER YOUR CURRENT FOOD RETURNED LOCATION AND PHONE NUMBER: ")
        print("IF WHAT YOU SAY IS TRUE, FOOD OR MONEY WILL BE RETURNED")
        o.Location_food(food)
        
    else:
        print("நாட்டு மருத்துவம் மற்றும்  தினம்தோறும் ஒரு உணவு செய்முறை :")
        print("நாட்டு மருத்துவம் : ")
        names={'((பதற்றம் மற்றும் வலிப்பு நோய் குறைய:'
               'அரைக்கீரையுடன் சுக்கு, இஞ்சி, மிளகு, மஞ்சள் ஆகியவற்றைச் சேர்த்து கஷாயம் செய்து குடித்தால் வலிப்பு நோய் குறையும் ))' ,
               '((அஜீரணம் : ஒரு டம்ளர் தண்ணீரில் கருவேப்பிலை, இஞ்சி, சீரகம், மூன்றையும் கொதிக்க வைத்து ஆறவைத்து வடிகட்டி குடிக்க அஜீரணம் சரியாகும்.))' ,
               '((குடல்புண்: மஞ்சளை தணலில் இட்டு சாம்பல் ஆகும் வரை எரிக்க வேண்டும். மஞ்சள் கரி சாம்பலை தேன் கலந்து சாப்பிட குடல் புண் ஆறும்.))',
               '((மூச்சுப்பிடிப்பு : சூடம், சுக்கு, சாம்பிராணி, பெருங்காயம் இவைகளை சம அளவு எடுத்து சேர்த்து வடித்த கஞ்சியில் கலக்கி மறுபடியும் சூடுபடுத்தி மூச்சுப்பிடிப்பு உள்ள இடத்தில் மூன்று வேளை தடவினால் குணமாகும்.))' ,
               '((தொண்டை கரகரப்பு :சுக்கு, பால் மிளகு, திப்பிலி, ஏலரிசி ஆகியவற்றை வறுத்து பொடி செய்து தேனில் கலந்து சாப்பிட தொண்டை கரகரப்பு குணமாகும்.))' }
        for name in names:
            print(name)
        print("********************************************************************************************************************************************************")
        print("தினம்தோறும் ஒரு உணவு செய்முறை :")
        print("சிக்கன் பிரியாணி செய்முறை : ")
        print(" தேவையான பொருட்கள் :1/2கிலோ கோழி கறி,2டம்ளர் பாஸ்மதி அரிசி,3 வெங்காயம்,2தக்காளி,2 பச்சை மிளகாய்,கையளவு கொத்தமல்லி,கையளவு புதினா,1/2ஸ்பூன் மஞ்சள் தூள்,1ஸ்பூன் மிளகாய் தூள்,தேவையானஅளவு உப்பு,3ஸ்பூன் இஞ்சி பூண்டு விழுது")
        print("1/4கப் தயிர்,1 பட்டை,2கிராம்பு,1பிரியாணி இலை,1ஸ்பூன் சோம்பு,1மராட்டி மொக்கு,3டம்ளர் தண்ணீர்,3மேசைக்கரண்டி எண்ணெய்,1மேசைக்கரண்டி நெய.")
        print("சமையல் குறிப்புகள்:")
        print("**********************************************************************************************************************************************************")
        print("1.கோழி கறியை நன்கு சுத்தம் செய்து மஞ்சள் தூள் சேர்த்து நன்கு சுத்தம் செய்து வைக்கவும். பாஸ்மதி அரிசியை தண்ணீர் ஊற்றி நன்கு கழுவி சுத்தம் செய்து தண்ணீர் ஊற்றி ஊற வைத்து கொள்ளவும். குக்கரில் எண்ணெய், நெய் ஊற்றி காய்ந்ததும் பட்டை, கிராம்பு, பிரியாணி இலை, சோம்பு, மராட்டி மொக்கு சேர்த்து தாளித்து நீளமாக நறுக்கிய வெங்காயம், பச்சை மிளகாய் சேர்த்து நன்கு பொன்னிறமாக வதக்கவும். பிறகு இஞ்சி பூண்டு விழுது, தக்காளி சேர்த்து வதக்கவும்.")
        print("**********************************************************************************************************************************************************")
        print("2.பிறகு உப்பு, மஞ்சள் தூள், மிளகாய் தூள் சேர்த்து நன்கு பச்சை வாசனை போகும் வரை வதக்கவும்.பிறகு புதினா,கொத்தமல்லி சேர்த்து நன்கு கிளறி விடவும்.தயிர் சேர்த்து நன்கு கலந்து விட்டு வதக்கவும். பிறகு நன்கு கலந்து எண்ணெய் பிரிந்து வந்ததும் கோழி கறியை சேர்த்து நன்கு கிளறி விடவும்.மூடி வைத்து கறி பாதி அளவு வெந்து தண்ணீர் விட்டு வரும் வரும் வரை வேக வைக்கவும்.")
        print("**********************************************************************************************************************************************************")
        print("3.பிறகு 3 டம்ளர் தண்ணீர் ஊற்றி உப்பு, காரம் சரிபார்த்து கொள்ளவும். தண்ணீர் ஒரு கொதி வந்ததும் பாஸ்மதி அரிசியை தண்ணீர் இல்லாமல் வடிகட்டி இதில் சேர்த்து கலந்து விடவும். சிறிதளவு கொத்தமல்லி, புதினா இலை சேர்த்து மூடி போட்டு 2 விசில் விட்டு இறக்கவும்.")
        print("******************************************************************************************************************************************************************")
        print("4.விசில் போனதும் மூடியை திறந்து பக்குவமாக கிளறி விட்டு 2 ஸ்பூன் நெய் ஊற்றி கிளறி 5 நிமிடம் மூடி வைத்து பிறகு சூடாக பரிமாறவும். சுவையான சிக்கன் பிரியாணி தயார்.")
        print(" ***************JR ONLINE FOOD ORDERING RESTAURANT THANK YOU COME AGAIN ***********")
        quit()



        

