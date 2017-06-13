#!/usr/bin/env python
#Partitoner

import sys
import pprint

trip_per_vendor = {}
Fare_amt_vendor = {}
Payment_methods = {}
Month_in_year = {}
Passenger_per_Vendor = {}

trip_file = sys.stdin

for linenum, line in enumerate(sys.stdin):
    if linenum != 0 and linenum!= 1:
        line = line.strip()
        vendor, trip_distance, Fare_Amt, Payment, Month, Passenger_Count = line.split('\t')

        if vendor in trip_per_vendor:
            trip_per_vendor[vendor].append(float(trip_distance))
        else:
            trip_per_vendor[vendor] = []
            trip_per_vendor[vendor].append(float(trip_distance))
        
        if vendor in Fare_amt_vendor:
            Fare_amt_vendor[vendor].append(float(Fare_Amt))
        else:
            Fare_amt_vendor[vendor] = []
            Fare_amt_vendor[vendor].append(float(Fare_Amt))
            
        if Payment in Payment_methods:
            Payment_methods[Payment].append(1)
        else:
            Payment_methods[Payment] = []
            Payment_methods[Payment].append(1)
            
        if Month in Month_in_year:
            Month_in_year[Month].append(float(trip_distance))
        else:
            Month_in_year[Month] = []
            Month_in_year[Month].append(float(trip_distance))


        if vendor in Passenger_per_Vendor:
            Passenger_per_Vendor[vendor].append(float(Passenger_Count))
        else:
            Passenger_per_Vendor[vendor] = []
            Passenger_per_Vendor[vendor].append(float(Passenger_Count))

#Reducer

print "no. of trips by vendor"
print

for vendor in trip_per_vendor.keys():
    trip = sum(trip_per_vendor[vendor])
    trip = trip/12
    print '%s\t%s' % (vendor, trip)

print "total fare by each vendor"
print

fare = 0.0
for vendor in Fare_amt_vendor.keys():
    fare = sum((Fare_amt_vendor[vendor]))
    fare = fare/12
    print '%s\t%s' % (vendor, fare)

print "mostly used payment types"
print
    
for Payment in Payment_methods.keys():
    times = sum(Payment_methods[Payment])
    print '%s\t%s' % (Payment, times)
    
print "average no. of passenger per month per day"
print

for Month in Month_in_year.keys():
    mnth = sum(Month_in_year[Month])/(len(Month_in_year[Month]))
    print '%s\t%s' % (Month, mnth)

print "total no. of passenger per vendor"
print

for vendor in Passenger_per_Vendor.keys():
    pnum = sum(Passenger_per_Vendor[vendor])
    print '%s\t%s' % (vendor, pnum)

