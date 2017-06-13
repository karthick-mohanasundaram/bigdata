#!/usr/bin/env python

import sys

# input comes from STDIN (standard input)
for linenum, line in enumerate(sys.stdin):
    if linenum != 0 and linenum!= 1:
        line = line.strip()
        line = line.split(",")

        vendor_name = line[0]
        trip_distance = line[4]
        Fare_Amt = line[12]
        Payment = line[11].lower()
        Date = (line[1])[0:7]
        Passenger_Count = line[3]
        print '%s\t%s\t%s\t%s\t%s\t%s' % (vendor_name, trip_distance,Fare_Amt, Payment, Date, Passenger_Count)
