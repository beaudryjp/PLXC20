int refer(int* x) {
   *x = *x * *x;
   return *x;
}
int a;
a = 7;
int b;
b = refer(&a);
print(a);
print(b);   
