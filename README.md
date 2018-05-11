## PROJECT PROBLEM DESCRIPTION

###### Strictly Java based inventory management system for The Career Services Office at the College at Brockport
> The Career Services Office at the College at Brockport provides a service to the students who need professional (business) clothing for interviews, etc. This service is provided through donated clothing to the Career Services office. An item of clothing donated is recorded in the inventory of the professional clothes closet maintained by the office. These items are then taken by registered Brockport students who might need them, after they find a suitable item. Alternatively, if no such item is currently available, the student is allowed to enter a request for an item of clothing. This will be recorded by the system. Once an item of clothing is donated, the administrator (user) of the system will go through pending requests, and see if any of them can be fulfilled by any of the items in inventory. If a suitable match is found, the requester will be contacted by Career Services using phone/email and asked to come by and pick up the item.

> Each item of clothing that comes in as a donation is barcoded. The barcode is a sequence of digits – the first digit will be a 0 or 1 (0 – to indicate it is an item intended for men, and 1 – to indicate it is an item intended for women). The rest of the digits is a combination of clothing article type (pant/pant suit/shirt/etc.), color and a sequence number. See the examples in the data model below for illustrations.

## PROJECT FEATURES

1. CRUD sql operations on 'Article Types'
   - Fields:
      - Id (Auto-generated Primary Key)
      - Description (Ex. "Pants")
      - Barcode Prefix (Ex. 01) - Foreign Key
      - Alpha code (Ex. P)
      - Status (Active/Inactive – Default: “Active”)
2. CRUD sql operations on 'Colors'
   - Fields:
      - Id (Integer) (Auto-generated PK)
      - Description (Ex. "Red")
      - Barcode Prefix (Ex. 01) - Foreign Key
      - Alpha code (Ex. R)
      - Status (Active/Inactive – Default: “Active”)
3. CRUD sql operations on 'Inventory'
   - Fields:
      - 1.	Barcode (ex. 00523001) (PK - 8 digit int)
           - First digit corresponds to the item's Gender (0 = Male, 1 = Female, 2 = Unisex)  
           - Next two digits corresponds to the item's aritcle type (barcode prefix)
           - Next two digits corresponds to the items's primary color (barcode prefix)
           - Last three digits corresponds to a count of that item in the inventory (multiple "male" "red" "shirt")
      - 2.	Gender (Text)
      - 3.	Size (Text)
      - 4.	Article Type (Integer) – Foreign Key of 'Article Type'
      - 5.	Color1 (Integer) – Foreign Key of 'Color'
      - 6.	Color2 (Integer) – Foreign Key of 'Color' (can be NULL or “”)
      - 7.	Brand (Text)
      - 8.	Notes (Text)
      - 9.	Status (“Donated”, “Received”, “Removed” – default is “Donated”)
      - 10.	Donor Lastname (Text)
      - 11.	Donor Firstname (Text)
      - 12.	Donor phone (Text)
      - 13.	Donor email (Text)
      - 14.	Receiver Netid (Text) - College Based ID
      - 15.	Receiver Lastname (Text)
      - 16.	Receiver Firstname (Text)
      - 17.	Date donated (Text)
      - 18.	Date taken (Text)
4. CRUD sql operations on 'Clothing Requests'
   - Fields:
      - 1.	Id (Integer) (Auto-generated PK)
      - 2.	Requester Netid (Text)
      - 3.	Requester Phone (Text)
      - 4.	Requester Lastname (Text)
      - 5.	Requester Firstname (Text)
      - 6.	Requested Gender (Text)
      - 7.	Requested Article Type (Integer) – Foreign Key of 'Article Type'
      - 8.	Requested Color1 (Integer) – Foreign Key of 'Color'
      - 9.	Requested Color2 (Integer) – Foreign Key of 'Color' (can be NULL or “”)
      - 10.	Requested Size (Text)
      - 11.	Requested Brand (Text)
      - 12.	Request Status (“Pending”, “Fulfilled”, “Removed”)
      - 13.	Fulfil Item Barcode (Text) – Pimary Key of 'Inventory'
      - 14.	Request Made Date (Text)
      - 15.	Request Fulfilled Date (Text)
5. Check out Inventory Item to Student
>The system retrieves the item of clothing in 'Inventory' using a entered/scanned barcode. The system then records student's netid, first name and last name. This information is then added to the 'Inventory' record corresponding to the item rerieved and the date taken value is computed as the date of check out. The Status is set to the value “Received”.
6. Fulfill a request
>The system retrives all pending requests, ordered by “Request Made Date”, from earliest to latest. The user will select a request and ask the system to see if it can be fulfilled. The system will check the “Inventory” rolodex/table to see if there are any clothing items that match the following:
   - 1.	Article type in 'inventory' table matches the article type in the request
   - 2.	Gender of the article type in the request
All these matching items will then be shown to the user. The user can select one of the matching Inventory items to fulfill the request. In this case, the system changes the Status of the request to “Fulfilled”, records this barcode as the Fulfil Item Barcode, and the current date as the “Request Fulfilled Date”. The system then changes the Status of this Inventory item to “Received”, record the requester’s last name, first name and netid as well as the the current date as date taken.
7. Reports which can be saved to a .csv file for excel viewing later
   - Top Donor
      - Retrives all the Donors from the 'Inventory' records with the number of items they have donated
   - Available Inventory
      - Retrives all 'Inventory' records that are available to give out (Status is "donated")
   - Check Out Items
      - Retirves all 'Inventory' records that have been given out up to a certain date
