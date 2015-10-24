#include <iostream>
#include <fstream>
#include <string>
#include <vector>
#include <iomanip>
using namespace std;

void compileOutPut(vector<char>);
//void executeOutPut(vector<char>);
void interactionMode();

int main(int argc, char *argv[]){

   if (argc <= 1){   // Check if Null Pointer
      cout << "Passed no argument.\n";
      //interactione mode
      interactionMode();
   }
   else{
      string arg = argv[1];
      string stringFile = argv[2];
      vector<char> symbol;

      if (arg == "-c"){ 
         ifstream fin(stringFile.c_str());  // Can now compile on g++
         if(fin.fail()){
            //Fail to read file
            cout << "Fail" << endl;
         }else{
            //Success read file
            char c;

            while(fin >> c){
               symbol.push_back(c);
            }
            compileOutPut(symbol);
         }
      }
      else if (arg == "-e"){
         ifstream fin(stringFile.c_str());
         if(fin.fail()){
            //Fail to read file
            cout << "Fail" << endl;
         }else{
            //Success read file
            char c;
            while(fin >> c){
               //symbol.push_back(c);
            }
            //executeOutPut(symbol);
         }
      }
      else{
         cout << "Argument not found." << endl;
      }
   }
   return 0;
}

void compileOutPut(vector<char> symbol){
   cout << "#include <iostream>\n" << endl;
   cout << "const int SIZE = 1000;" << endl;
   cout << "int tape[SIZE] = {0};" << endl;
   cout << "int *dp = tape;\n" << endl;
   cout << "int main() {" << endl;

   int count = 4;

   for(size_t i = 0; i < symbol.size(); i++)  // std::size_t fixed warning
   {
      switch(symbol[i])
      {
      case '+':
         cout << setw(count) << "" << "++*dp;" << endl; 
         break;
      case '-':
         cout << setw(count) << "" << "--*dp;" << endl;
         break;
      case '>':
         cout << setw(count) << "" << "++dp;" << endl;
         break;
      case '<':
         cout << setw(count) << "" << "--dp;" << endl;
         break;
      case ':':
         cout << setw(count) << "" << "std::cout << *dp;" << endl;
         break;
      case '.':
         cout << setw(count) << "" << "std::cout << ((char) *dp);" << endl;
         break;
      case '{':
         cout << setw(count) << "" << "while (*dp) {" << endl;
         count += 4;
         break;
      case '}':
         cout << setw(count - 4) << "" << "}" << endl;
         count -= 4;
         break;
      default:
         break;
      }
   }

   cout << "    return 0;" << endl;
   cout << "}" << endl;
}

/*
void executeOutPut(vector<char> symbol){

const int SIZE = 1000;
int tape[SIZE] = {0};
int *dp = tape;

for(int i = 0; i < symbol.size(); i++)  //Also size_t
{
switch(symbol[i])
{
case '+':
cout << "\t++*dp;" << endl; 
break;

case '-':
cout << "\t--*dp;" << endl;
break;
case '>':
cout << "\t++dp;" << endl;
break;
case '<':
cout << "\t--dp;" << endl;
break;
case ':':
cout << "\tstd::cout << *dp;" << endl;
break;
case '.':
cout << "\tstd::cout << ((char) *dp);" << endl;
break;
case '{':
cout << "\twhile (*dp) {" << endl;
break;
case '}':
cout << "\t}" << endl;
break;
default:
break;
}
}
}
*/

void interactionMode(){

   cout << "Position: ";
   for(int i = 0; i < 10; i++){
      cout << i << " ";
   }

   cout << "\n";

   cout << "Characters: ";
   for(int i = 0; i < 10; i++){
      cout << i << " ";
   }

   cout << "Integer: ";
   for(int i = 0; i < 10; i++){
      cout << i << " ";
   }

   cout << "Pointer: ";
   for(int i = 0; i < 10; i++){
      cout << i << " ";
   }

}
