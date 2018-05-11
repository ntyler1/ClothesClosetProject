## PROJECT PROBLEM DESCRIPTION

###### Strictly Java based inventory management system for The Career Services Office at the College at Brockport
> The Career Services Office at the College at Brockport provides a service to the students who need professional (business) clothing for interviews, etc. This service is provided through donated clothing to the Career Services office. An item of clothing donated is recorded in the inventory of the professional clothes closet maintained by the office. These items are then taken by registered Brockport students who might need them, after they find a suitable item. Alternatively, if no such item is currently available, the student is allowed to enter a request for an item of clothing. This will be recorded by the system. Once an item of clothing is donated, the administrator (user) of the system will go through pending requests, and see if any of them can be fulfilled by any of the items in inventory. If a suitable match is found, the requester will be contacted by Career Services using phone/email and asked to come by and pick up the item.

> Each item of clothing that comes in as a donation is barcoded. The barcode is a sequence of digits – the first digit will be a 0 or 1 (0 – to indicate it is an item intended for men, and 1 – to indicate it is an item intended for women). The rest of the digits is a combination of clothing article type (pant/pant suit/shirt/etc.), color and a sequence number. See the examples in the data model below for illustrations.

## PROJECT FEATURES

1. Basic CRUD sql operations on 'Article Types'
   - Fields:
      - Id (Integer) (Auto-generated PK)
      - Description (Text)
      - Barcode Prefix (Text) - Unique
      - Alpha code (Text)
      - Status (Active/Inactive – Default: “Active”)

   - Examples:
      - Id	     &nbsp;    Description	               Barcode Prefix	        Alpha code	      Status
      -  1	          Pant Suit	                     01	                  PS	             Active
      -  2	          Skirt Suit             	      02          	      SS	             Active
      -  3	          Blazer	                        03	                  BL	             Active
      -  4	          Dress	                        04	                   D	             Active
