int refer(int** x) {
   int *p;
   *p = 12;
   **x = *p + **x;
   return *p;
}
int* a;
int b;
*a = 4;
b = refer(&a);
print(*a);
print(b);

