# server2.py : 계속 보낼값 입력해 보낼 수 있음.
import socket
import time
import select

host = '172.31.33.121' # Symbolic name meaning all available interfaces
port = 5656 # Arbitrary non-privileged port
 

server_sock = socket.socket(socket.AF_INET,socket.SOCK_STREAM)#socket.SOCK_STREAM
server_sock.setsockopt(socket.SOL_SOCKET,socket.SO_REUSEADDR,1)#assure reconnect
server_sock.bind((host, port))
server_sock.listen(5)
print(server_sock)
sockets_list = [server_sock]#socket list... multiple clients 

clients = {}#socket is the key, user Data will be value 

print("기다리는 중")
# client_sock, addr = server_sock.accept()

# print('Connected by', addr)

# 서버에서 "안드로이드에서 서버로 연결요청" 한번 받음
# data = client_sock.recv(1024)
# print(data.decode("utf-8"), len(data))

# while(True):
#     data2 = str(input("보낼 값 : "))
#     #print(data2.encode())
#     #client_sock.send(data)
#     #client_sock.send(data2.to_bytes(4, byteorder='little'))
#     message_to_send = data2.encode("UTF-8")
#     client_sock.send(len(message_to_send).to_bytes(2, byteorder='big'))
#     client_sock.send(message_to_send)

#     # 값하나 보냄(사용자가 입력한 숫자)
#     #client_sock.send(data2.to_bytes(4, byteorder='little'))
#     #client_sock.send(struct.pack(data2, len(data)))
#     # 안드로이드에서 값 받으면 "하나받았습니다 : 숫자" 보낼 것 받음
#     data = client_sock.recv(1024)
#     print(data.decode("utf-8"))
    
#     if(data2 == "quit"):
#         break;

# # 연결끊겠다는 표시 보냄
# #i=99
# #client_sock.send(i.to_bytes(4, byteorder='little'))
# client_sock.close()
# server_sock.close()



def receive_message(client_socket):
	try:
		# message_header = client_socket.recv(HEADER_LENGTH)

		# if not len(message_header):
		# 	return False

		# message_length = int(message_header.decode('utf-8').strip())
		#return {"header" : message_header, "data" : client_socket.recv(message_length)}
		data = client_socket.recv(1024)
		
		if not len(data):
			return False

		print("receive Message : " +data.decode('utf-8'))
		#return data.decode('utf-8')
		return {"data" : data}

	except:
		return False



while True:
	read_sockets, _, exception_sockets = select.select(sockets_list, [], sockets_list)#read, write ,air on 

	for notified_socket in read_sockets:
		if notified_socket == server_sock: #someone just connected 
			client_socket, client_address = server_sock.accept()
			print(client_socket)
			print(client_address)

			user = receive_message(client_socket)
			if user is False:
				print("USER FALSE")
				continue

			sockets_list.append(client_socket)

			clients[client_socket] = user  # [client_socket : {data : data}] set user data when user connected 
			print("ACCEPTED connection")
			#print(f"Accepted new connection fron {client_address[0]}:{client_address[1]} username : {user['data'].decode('utf-8')} ")

		else: 
			message = receive_message(notified_socket)
			#print("IN ELSE : " + message['data'].decode('utf-8'))
			if message is False :
				#print(f"Closed connection from {clients[notified_socket]['data'].decode('utf-8')}")
				sockets_list.remove(notified_socket)
				del clients[notified_socket]
				continue

			user = clients[notified_socket] # same with message above, notified socekt is a socket that sends the data
		

			#print(f"received message from {user['data'].decode('utf-8')} : {message['data'].decode('utf-8')}")

			#share this message with everyBody
			print("@@@@@@@@@@@")
			print(str(clients))
			print("@@@@@@@@@@@")
			for client_socket in clients:
				#print("for loop")
				
				print("------------------------")
				if client_socket != notified_socket:
					message_to_send = message['data']
					# client_socket.sendall( len(message_to_send).to_bytes(2, byteorder='big') )
					# client_socket.sendall(message_to_send)
					# data2 = "hello from the other side~()
					# message_to_send = data2.encode("UTF-8")
					print(client_socket)
					print(user['data'])#data that user send when they first connect
					print("*****Message from" + str(message_to_send) + " AND " + str(len(message_to_send)))
					#print("len" + str(len(message_to_send)))
					#client_socket.send(len(message_to_send).to_bytes(2, byteorder='big'))
					#print("bytes"+ str(len(message_to_send).to_bytes(2, byteorder='big')))
					client_socket.send(message_to_send)
					#print("final" + str(message_to_send))
					#client_socket.send(user['header'] + user['data'] + message['header'] + message['data'])
				


	for notified_socket in exception_sockets:
		sockets_list.remove(notified_socket)
		del clients[notified_socket]




