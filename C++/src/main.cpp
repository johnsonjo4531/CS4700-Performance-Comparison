/*
 *  No License.
 * 
 * 
 */

/* 
 * File:   main.cpp
 * Author: Jaden Miller
 *
 * Created on March 11, 2017, 2:14 PM
 */

#include <cstdlib>
#include <inttypes.h>
#include <math.h> // For sqrt
#include <stdio.h> // For printf
#include <string.h> // For memset
#include <ctime> // For timing fuctions


#define DIV_MICROSECONDS 1000000
#define DIV_MILLISECONDS 1000

// ------------- ProtoTypes --------------------
float_t eratosthenes(uint32_t n);
void printStatistics(float data[], uint32_t n, uint32_t loops);

/*
 * Main Entry
 */
int main(int argc, char** argv)
{
   const uint32_t nValsNumber = 7;
   const uint32_t numberLoops = 100;
   
   uint32_t nVals[nValsNumber] = {1, 10, 100, 1000, 10000, 100000, 1000000};
   float_t performance[numberLoops];
   
   // Run each n for number of loops while saving performance then print the performance result
   for (uint32_t i = 0; i < nValsNumber; i++)
   {
      for (uint32_t j = 0; j < numberLoops; j++)
      {
         performance[j] = eratosthenes(nVals[i]);
      }
      printStatistics(performance, nVals[i], numberLoops);
   }
   return 0;
}

/**
 * Executes the Eratosthenes function for the given n
 * @param n
 * @return The time (in micro seconds) taken to perform
 */
float_t eratosthenes(uint32_t n)
{
   // Scope variables
   float_t performance_mSec;
   uint64_t primeIndex = 0;
   std::clock_t start;
   
   // Start performance metric
   start = std::clock();
   
   // Allocate memory
   bool* theBuf = new bool[n + 1];
   uint64_t* primes = new uint64_t[n + 1];
   memset(theBuf, 1, n);
   
   // Main Eratosthenes function
   for (uint64_t i = 2; i <= sqrt(n); i++)
   {
      if (theBuf[i])
      {
         for (uint64_t j = 2 * i; j <= n; j += i)
         {
            theBuf[j] = false;
         }
      }
   }
   
   // Create a buffered list of all the primes computed
   for (uint64_t i = 2; i < n; i++)
   {
      if (theBuf[i])
      {
         primes[primeIndex++] = i;
      }
   }
   
   
   // Calculate performance in micro seconds
   performance_mSec = (std::clock() - start) / (double)(CLOCKS_PER_SEC / DIV_MILLISECONDS);
   
   // Do cleanup and return
   delete [] theBuf; 
   return performance_mSec;
}


void printStatistics(float data[], uint32_t n, uint32_t loops)
{
   // Scope variables
   float sum = 0.0, mean, standardDeviation = 0.0;

   for(uint32_t i = 0; i < loops; ++i)
   {
       sum += data[i];
   }
   mean = sum / loops;

   for(uint32_t i = 0; i < loops; ++i)
   {
     standardDeviation += pow(data[i] - mean, 2);
   }

   standardDeviation /= loops;
   standardDeviation = sqrt(standardDeviation)
   printf("For n=%d, runtime: %f ms with standard deviation of %f ms\n", n, mean, standardDeviation);
}



