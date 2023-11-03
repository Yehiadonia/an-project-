
import matplotlib.pyplot as plt
import numpy as np
import time

# Question 1 (a) Naive Iterative Method
def naive_power(base, exponent):
    result = 1
    for _ in range(exponent):
        result *= base
    return result

# Question 1 (a) Divide-and-Conquer Method
def divide_conquer_power(base, exponent):
    if exponent == 0:
        return 1
    if exponent % 2 == 0:
        half_power = divide_conquer_power(base, exponent // 2)
        return half_power * half_power
    else:
        half_power = divide_conquer_power(base, (exponent - 1) // 2)
        return half_power * half_power * base

# Question 2 (a) Merge Sort Algorithm
def merge_sort(arr):
    if len(arr) > 1:
        mid = len(arr) // 2
        L = arr[:mid]
        R = arr[mid:]

        merge_sort(L)
        merge_sort(R)

        i = j = k = 0

        while i < len(L) and j < len(R):
            if L[i] < R[j]:
                arr[k] = L[i]
                i += 1
            else:
                arr[k] = R[j]
                j += 1
            k += 1

        while i < len(L):
            arr[k] = L[i]
            i += 1
            k += 1

        while j < len(R):
            arr[k] = R[j]
            j += 1
            k += 1

# Question 2 (a) Binary Search Algorithm
def binary_search(arr, low, high, x):
    if high >= low:
        mid = (high + low) // 2
        if arr[mid] == x:
            return mid
        elif arr[mid] > x:
            return binary_search(arr, low, mid - 1, x)
        else:
            return binary_search(arr, mid + 1, high, x)
    else:
        return -1

# Question 2 (a) Finding Pairs with Given Sum
def find_pairs_with_sum(arr, sum):
    merge_sort(arr)
    pairs = []
    for i in range(len(arr)):
        complement = sum - arr[i]
        if binary_search(arr, i + 1, len(arr) - 1, complement) != -1:
            pairs.append((arr[i], complement))
    return pairs

# Experimentation and Plotting
def run_experiments():
    naive_times = []
    divide_times = []
    ns = np.arange(1, 1000000, 100000)
    for n in ns:
        start = time.time()
        naive_power(2, n)
        end = time.time()
        naive_times.append(end - start)

        start = time.time()
        divide_conquer_power(2, n)
        end = time.time()
        divide_times.append(end - start)

    plt.plot(ns, naive_times, label='Naive Method')
    plt.plot(ns, divide_times, label='Divide and Conquer Method')
    plt.xlabel('Power Size n')
    plt.ylabel('Running Time (seconds)')
    plt.legend()
    plt.show()

if __name__ == "__main__":
    run_experiments()
