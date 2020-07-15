# server2.py : 계속 보낼값 입력해 보낼 수 있음.
import socket
import time
import select

host = 'MyIp' # Symbolic name meaning all available interfaces
port = 5656 # Arbitrary non-privileged port
 

server_sock = socket.socket(socket.AF_INET,socket.SOCK_STREAM)#socket.SOCK_STREAM
server_sock.setsockopt(socket.SOL_SOCKET,socket.SO_REUSEADDR,1)#assure reconnect
server_sock.bind((host, port))
server_sock.listen(5)#maximum number of clients
print(server_sock)
sockets_list = [server_sock]#socket list... multiple clients 

clients = {}#socket is the key, user Data will be value 
receiver_clients = {}#clients 중에서 receiver를 담을 딕셔너리

print("기다리는 중")

def receive_message(client_socket):
	try:
		data = client_socket.recv(1024)#receive data
		
		if not len(data):
			return False

		print("receive Message : " +data.decode('utf-8'))
		return {"data" : data}

	except:
		return False



while True:
	read_sockets, _, exception_sockets = select.select(sockets_list, [], sockets_list)#read, write ,air on/ 연결한 클라이언트들의 소켓을 셀렉

	for notified_socket in read_sockets:
		if notified_socket == server_sock: #someone just connected 
			client_socket, client_address = server_sock.accept()#accept connection
			print(client_socket)
			print(client_address)

			user = receive_message(client_socket)#receive message
			if user is False:
				print("USER FALSE")
				continue

			print(user['data'].decode('utf-8') + " is connected ")
			userType = user['data'].decode('utf-8')#유저가 receiver(보호자) 인지 아니면 sender(환자)인지 
			print(userType)

			if userType == 'receiver':
				print('pass receiver')
				receiver_clients[client_socket] = user#receiver 라면 receiver_clients에 담아준다
				print("ACCEPTED AS receiver")

			sockets_list.append(client_socket)

			clients[client_socket] = user  #clients에 데이터를 담아준다

		else: 
			message = receive_message(notified_socket)#누군가가 data를 보냈다면 

			if message is False :
				sockets_list.remove(notified_socket)
				del clients[notified_socket]
				continue

			user = clients[notified_socket] # same with message above, notified socekt is a socket that sends the data


			#print(f"received message from {user['data'].decode('utf-8')} : {message['data'].decode('utf-8')}")

			#share this message with everyBody
			print("@@@@@@@@@@@")
			print(str(clients))
			print("@@@@@@@@@@@")
			for client_socket in receiver_clients:#clients 중에서 receiver 에게만 메세지를 보냄 
				#print("for loop")
				
				print("------------------------")
				if client_socket != notified_socket:
					message_to_send = message['data']
					
					print(client_socket)
					print(user['data'])#data that user send when they first connect
					print("*****Message from" + str(message_to_send) + " AND " + str(len(message_to_send)))
					client_socket.send(message_to_send)#send message
					
				


	for notified_socket in exception_sockets:
		sockets_list.remove(notified_socket)
		del clients[notified_socket]




