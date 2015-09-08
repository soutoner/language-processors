

int p[3];
p[0]=1;
p[1]=2;
p[2]=3;
int i;
for (i=0;i<3;i++){
	switch(p[i]){
		case 1:
			print(0);
			break;
		case 2: 
			print(2);
			break;
		default:
			print(45);

	}
}
