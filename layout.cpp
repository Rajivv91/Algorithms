// Howdy!
//
// Thank you for your interest in FrogSlayer and taking the time to apply.
//
// This file is a coding exercise. Please follow the instructions explicitly -
// you may only write code where specified. We've included reference material
// with information to produce a working solution. This is not a trick problem;
// it is solvable within the given constraints.
//
// It's okay if you can't get a fully working solution; just submit what you
// have. We're more interested in seeing how well you can learn and use
// something new with limited direction.
//
// Instructions:
//
// Only writing code where indicated (the bodies of the `extract_x`,
// `extract_y`, `call_foo`, and `call_bar` functions), make this program output
// the values of:
//
//   * `thing->x`
//   * `thing->y`
//   * `thing->foo()`
//   * `thing->bar()`
//
// Some useful references:
//
// https://en.wikipedia.org/wiki/Virtual_method_table
// http://www.openrce.org/articles/full_view/23
// https://en.wikipedia.org/wiki/Pointer_(computer_programming)#C_arrays
// https://en.wikipedia.org/wiki/Pointer_(computer_programming)#Function_pointer


#include <cstdio>

class Thing
{
private:
	int x;
	int y;
	virtual int foo()
	{
		return x+y;
	}
	virtual int bar()
	{
		return x*y;
	}
public:
	Thing(){
		x = 2;
		y = 10;
	}
};

int extract_x(void *thing)
{
   // --- Begin your code --- 
   return *((int*) ((char*)thing + 8));
    // --- End your code   ---
}

int extract_y(void *thing)
{
    // --- Begin your code ---
     return *((int*) ((char*)thing + 12));
    // --- End your code   ---
}

int call_foo(void *thing)
{
    // --- Begin your code ---
    //point to virtual table in class Thing
char (**virtualtable)(Thing*) = (char (**)(Thing*)) *((void**) thing);
//reference foo 0 in virtual table
char (*foo)(Thing*) = virtualtable[0];
//return function pointer foo
return (*foo)((Thing*)thing);
// --- End your code   ---
}

int call_bar(void* thing)
{
    // --- Begin your code ---
    char (**virtualtable)(Thing*) = (char (**)(Thing*)) *((void**) thing);
char (*bar)(Thing*) = virtualtable[1];
return (*bar)((Thing*)thing);
    // --- End your code   ---
}

int main()
{
	Thing thing;
    std::printf("%d %d %d %d\n",
                extract_x(&thing),
                extract_y(&thing),
                call_foo(&thing),
                call_bar(&thing));
	return 0;
}
