#include<stdio.h>
#include<stdlib.h>
#include<string.h>
#include<sys/socket.h>
#include<arpa/inet.h>
#include<unistd.h> 

 
char* count_vowels(char* phrase){
    int i,vowels;
    vowels=0;
    char* result;
    char count[100];
    
    
    for(i=0;phrase[i]!='\0';++i)
    {
        if(phrase[i]=='a' || phrase[i]=='e' || phrase[i]=='i' || phrase[i]=='o' || phrase[i]=='u' || phrase[i]=='A' || phrase[i]=='E' || phrase[i]=='I' || phrase[i]=='O' || phrase[i]=='U')
            ++vowels;
    }
    
    
    sprintf(count,"%d",vowels);

    result = malloc(strlen(count) + 1);
   
    strcpy(result, count);
    
    return result;
    
}

char* count_consonants(char* phrase){
    int i,consonants;
    consonants=0;
    char* result;
    char count[100];
    
    for(i=0;phrase[i]!='\0';++i)
    {
        if((phrase[i]>='a'&& phrase[i]<='z') || (phrase[i]>='A'&& phrase[i]<='Z')){
            if(phrase[i]!='a' && phrase[i]!='e' && phrase[i]!='i' && phrase[i]!='o' && phrase[i]!='u' && phrase[i]!='A' && phrase[i]!='E' && phrase[i]!='I' && phrase[i]!='O' && phrase[i]!='U')
                ++consonants;
        }
            
    }
    
    sprintf(count,"%d",consonants);

    result = malloc(strlen(count) + 1);
   
    strcpy(result, count);
    
    return result;
}

int main(int argc , char *argv[])
{
    int socket_desc , client_sock , c , read_size;
    struct sockaddr_in server , client;
    char client_message[1024];
    char* resultTreatment;
     
    //Create socket
    socket_desc = socket(AF_INET , SOCK_STREAM , 0);
    if (socket_desc == -1)
    {
        printf("Création de la socket failed");
    }
    puts("Socket créée");
     
    //Prepare the sockaddr_in structure
    server.sin_family = AF_INET;
    server.sin_addr.s_addr = INADDR_ANY;
    server.sin_port = htons( 15000 );
     
    //Bind
    if( bind(socket_desc,(struct sockaddr *)&server , sizeof(server)) < 0)
    {
        //print the error message
        perror("Bind failed");
        return 1;
    }
    puts("Bind réussi.");
    
    //Listen
    listen(socket_desc , 3);
    while(1){
		memset (client_message, 0, sizeof (client_message)); // vider client_message
		//Accept and incoming connection
		puts("En attente de connexions...");
		c = sizeof(struct sockaddr_in);
		
		//accept connection from an incoming client
		client_sock = accept(socket_desc, (struct sockaddr *)&client, (socklen_t*)&c);
		if (client_sock < 0)
		{
			perror("Acceptation failed");
			return 1;
		}
		puts("Connection accepté");
		//Receive a message from client
		while( (read_size = recv(client_sock , client_message , 1024 , 0)) > 0 )
		{	
			int choix = client_message[0];
			if((choix-48)==3){ //-48 pour passer d'ASCII à digits
				resultTreatment = count_vowels(client_message+2);
				printf("Nombre de voyelle = %s\n", resultTreatment);
			}
			else if((choix-48)==4){ //-48 pour passer d'ASCII à digits
				resultTreatment = count_consonants(client_message+2);
				printf("Nombre de consonne = %s\n", resultTreatment);
			}
			strcat(resultTreatment,"\n\r");
			//write(client_sock , resultTreatment, strlen(resultTreatment)); //Envoi de la réponse au client.
			
			send(client_sock , resultTreatment , 1024 , 0);
			break;
		}
	   if(read_size == 0)
		{
			puts("Client disconnected");
			fflush(stdout);
		}
		else if(read_size == -1)
		{
			perror("recv failed");
		}
	}
    return 0;
}
