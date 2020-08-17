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
sender_receiver_ID = {}#key is client scoekt and the value is receivers' ID list 
ID_socket_convert = {}#ID is the key and the socket is the value 

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

def get_receivers_from_sender(client_socket):#get user data 
	try:
		data = client_socket.recv(1024)#receive data
		
		if not len(data):
			return False

		print("get_receivers_from_sender : " +data.decode('utf-8'))

		input_lists= [x.strip() for x in data.decode('utf-8').split(',')]

		print(input_lists)

		ID_socket_convert[input_lists[1]] = client_socket

		if input_lists[0] == 'sender':
			sender_receiver_ID[client_socket] = input_lists#sender라면 자기 보호자 리스트의 아이디를 넣어줌, 


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

			# if userType == 'receiver':
			# 	print('pass receiver')
			# 	receiver_clients[client_socket] = user#receiver 라면 receiver_clients에 담아준다
			# 	print("ACCEPTED AS receiver")

			get_receivers_from_sender(client_socket)

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

			# for receivers in sender_receiver_ID[notified_socket]:
			# 	for receiverID in receivers :
			# 		message_to_send = message['data']
			# 		print("receivers" )
			# 		if receiverID in ID_socket_convert:
			# 			ID_socket_convert[receiverID].send(message_to_send)

					
			
				


	for notified_socket in exception_sockets:
		sockets_list.remove(notified_socket)
		del clients[notified_socket]




