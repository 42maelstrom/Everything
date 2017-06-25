import math, sys

def dictionary(file_path):
    return open(file_path, 'r').readlines()

def find_sort_of_palindromes():
    global lookup
    lookup = Lookup(all_length_perms, range(17))
    l = [x.strip() for x in dictionary('Dictionary.txt')]
    l.sort(key = len)
    for s in l:
        if len(s) <= 17:
            t = is_sort_of_palindrome(s)
            if t:
                print(s)
                print(t)

class Lookup():
    def __init__(self, f, values):
        self.f = f
        self.table = {}
        for v in values:
            print(str(v))
            self.table[v] = f(v)
        
    def f(self, value):
        if value in self.table:
            return self.table[value]
        else:
            return self.f(value)
           
def is_sort_of_palindrome(s):
    l = len(s)
    #global lookup
    #length_options = lookup.f(l)
    length_options = all_length_perms(l) 
    for perm in length_options:
        if len(perm) < 4:
            continue
        if is_palindrome(perm):
            word = []
            w = s
            for n in perm:
                word.append(w[0:n])
                w = w[n:]
            if is_palindrome(word):
                return word
    return False

def is_palindrome(l):
    mid = int(math.ceil(len(l) / 2))
    return l[:mid] == l[-1:mid:-1]   
 

  
def all_length_perms(length, min_sub = 2):
    l = length
    m = min_sub
    
    perms = [[length]]
    for sub in range(m, l - m + 1):
        sub_perms = all_length_perms(l - sub, m)
        for sp in sub_perms:
            perms.append([sub] + sp)
    
    return perms

# while True:
#     s = input(' ')
#     print(str(is_sort_of_palindrome(s)))
find_sort_of_palindromes()