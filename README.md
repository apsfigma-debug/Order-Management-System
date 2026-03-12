# Order-Management-System

Project ini dibuat sepenuhnya dengan bantuan AI, baik dalam membuat struktur file dan folder, pemahaman kode dan struktural, dan pembuatan kode.

## Daftar Isi
1. Arsitektur Singkat
2. Cara Menjalankan Project
3. @PrePersist dan @PreUpdate vs @EntityListener
4. @Transactional(ReadOnly = True)
5. Command Pattern
6. @Scope("Prototype")
7. Mono, non-blocking, WebClient


### 1. Arsitektur Singkat
Pada Project ini saya menggunakan struktur file terpisah. Dimana Order-Service dan Product-Service tidak berada dalam 1 repository(Multi-Module Maven) namun disatukan dengan DockerCompose.
Project ini memiliki arsitektur seperti berikut:
- Order-Management-Service memiliki 3 folder dan 1 file, folder docker untuk menginisialisasikan database, folder Order-Service, folder Product-Service, dan file Docker-Compose.yml
- Pada Struktur utama Order-Service, terdapat 9 folder dan 1 file utama OrderServiceApplication.java. Dimana ada folder config yang menjadi penghubung antara Order-Service dan Product-Service, lalu ada folder Client yang menjadi penghubung antara WebClient dengan Command di Order-Service. Lalu command menjadi penghubung antara controller dan service, sehingga controller tidak berhubungan secara langsung dengan service. Lalu ada folder dto yang digunakan untuk menyimpan class class yang digunakan secara khusus untuk memindahkan data antar layer. Lalu ada folder entity yang menyimpan class yang digunakan sebagai entitas data yang digunakan pada Order-Service. Lalu ada Exception yang berisi class exception seperti GlobalExceptionHandler yang mengatur pesan yang akan ditampilkan jika terdapat error, InsufficientStock, OrderCannotBeCancelled, OrderCannotBeConfirmed, OrderNotFound, ProductNotFound. Lalu ada folder repository, dan ada folder Service.
- Pada struktur utama Product-Service ada 7 folder dan 1 file utama ProductServiceApplication.java. Dimana ada folder Command yang menghubungkan antara controller dan Service, lalu ada dto yang menyimpan data untuk transfer antar layer, lalu ada entity yang menyimpan entitas data yang akan digunakan, lalu ada exception untuk menyimpan class untuk menangani error, dan ada folder repository

Project ini menggunakan aplikasi aplikasi dengan versi sebagai berikut:
- Apache Maven : 4.0
- Java :21
- Spring-Boot : 3.5.1

### 2. Cara Menjalankan Project
Project dibuat dengan urutan sebagai berikut:
- Menggunakan AI untuk mengetahui struktur folder dan file serta struktur kode Order-Management-System.
- Membuat file Spring-boot Order-Service dan Product-Service secara otomatis dengan menggunakan VsCode.
- Membuat Docker-Compose dengan bantuan AI
- Membuat Product-Service tanpa Command Pattern dengan bantuan AI (Command Pattern yang dibuat tidak berhubungan dengan Order-Service)
- Membuat Order-Service dengan bantuan AI
- Memperbaiki Product-Service dengan Command Pattern dengan melihat Order-Service sebagai acuan dalam pembuatan Command Pattern(Command Pattern yang dibuat tidak berhubungan dengan Order-Service)
- Memperbaiki Product-Service dan Order-Service dalam penggunakan Command Pattern sehingga Command Pattern menjadi penghubung antara Controller dan Service Layer
- Memperbaiki Error yang muncul karena merubah Command Pattern yang sebelumnya tidak berhubungan dengan Service Layer, dimana terdapat kesalahan dalam pengkoneksiannya
- Memperbaiki Error yang muncul dimana totalPrice yang didapatkan terlalu besar, sehingga diubah nilai Precision di Order Entity dari 10 menjadi 19
- Memperbaiki Error dimana Data sudah terinput dan dibuat di Order-Service tapi nilai stock di Product-Service tidak berkurang, dimana error ini terjadi karena adanya kesalahan koding di CreateOrderCommand dan OrderService

### 3. @PrePersist dan @PreUpdate vs @EntityListener
Saya memilih @PrePersist dan @PreUpdate karena memberikan kemudahan dalam penggunaannya, karena @PrePersist dan @PreUpdate akan mengurus entity secara mandiri.

### 4. @Transactional(ReadOnly = True)
Kenapa @Transactional(ReadOnly = True) penting, karena ketika dilakukan hanya @Transactional tanpa ReadOnly, project melakukan kerja lebih keras dan lebih banyak dibandingkan dengan menggunakan ReadOnly. Ketika dilakukan ReadOnly project hanya mengambil data dari database saja, namun ketika dilakukan tanpa ReadOnly project melakukan kerja ekstra dengan mengambil data dari database, menyimpan di memory, memantau perubahan data, melakukan perbandingan data lama dengan data baru, dan mengirim perubahan ketika ada perubahan.

### 5. Command Pattern
Command Pattern dipilih karena dapat mempersingkat proses, dimana tidak akan ada pengulangan proses untuk kegiatan yang berbeda. 
Keuntungan Konkret yang didapatkan adalah:
- Controller menjadi lebih tipis dan lebih ringan
- Bisa dipakai dari file mana saja

Command yang digunakan pada Order-Service ada 
- GetOrderCommand : untuk mencari data Order berdasarkan id
- GetAllOrderCommand : untuk menampilkan seluruh data Order
- CreateOrderCommand : untuk membuat order baru dengan product id dari product-service
- CancelOrderCommand : mengubah status data dengan id dari pending pada data menjadi cancel
- ConfirmOrderCommand : mengubah status data dengan id dari pending menjadi confirm

Command yang digunakan pada Product-Service ada
- CreateProductCommand : membuat data baru 
- DeductStockCommand : untuk mengurangi nilai stock yang digunakan pada Order-Service berdasarkan id
- DeleteStockCommand : untuk menghapus data berdasarkan id
- GetAllProductCommand : untuk menampilkan seluruh data 
- GetProductCommand : untuk menampilkan data berdasarkan id
- UpdateProductCommand : untuk mengubah data berdasarkan id

### 6. @Scope("Prototype")
@Scope("Prototype") sangat penting karena tidak akan terjadinya overwrite request pada data yang lain. Sehingga tidak akan terjadinya kebingungan pada request yang dikirimkan atau diterima.

### 7. Mono, Non-Blocking, WebClient
Mono dalam project reactor adalah memberikan nilai 0 atau 1 secara asynchronous dan nonblocking adalah membiarkan data dapat terus berjalan tanpa perlu menunggu data sebelumnya selesai. WebClient mencapai ini dengan melalui beberapa proses untuk dapat mencapai non-blocking.
WebClient memberikan keringanan dalam memori dan usage, serta dapat dilakukan retry otomatis. Tak hanya itu, HTTP Konvensional tidak cocok untuk aplikasi microservice karena memakan banyak resource.
