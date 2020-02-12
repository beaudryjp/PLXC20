int a =7;
int b =5;
int* refer(int* x, int b) {
   *x = *x * b;
   return x;
}
b = *refer(&a, b);
print(a);
print(b);

