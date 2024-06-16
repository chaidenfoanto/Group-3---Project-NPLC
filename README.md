<h1 align="center">Group-6---Project-NPLC</h>

# Introduction👋
NPLC adalah kompetisi nasional bergengsi yang diselenggarakan oleh Universitas Ciputra, di bawah naungan Student Union dari Departemen Informatika. Kompetisi ini bertujuan sebagai platform untuk mengumpulkan tim-tim dari sekolah menengah atas di seluruh negeri, bersaing dalam Competitive Logic. Peserta berasal dari tingkat SMA yang setara dan bertujuan untuk menjadi juara di wilayah mereka masing-masing: Wilayah Timur dan Wilayah Barat. Dimana Competitive logic ini dirancang dalam bentuk Rally Games. Rally Games ini mencakup dua jenis permainan yaitu: Duel dan Tunggal.

### Games Single
#### System 
- Permainan dimainkan oleh 1 tim.
- Setiap tim hanya bisa bermain 1x di setiap pos. Kecuali, tim memiliki Skill Card: Second Chance.
- Tim dapat meraih bintang 1, 2 atau 3 sesuai dengan aturan dari setiap Rally Games.
  
### Games Duel
#### System 
- Permainan dimainkan oleh 2 tim yang akan saling beradu.
- Tim yang menang tidak bisa bermain lagi di game duel yang sama, sedangkan tim yang kalah masih punya 1 kesempatan main.
- Tim yang menang akan mendapatkan bintang 3 dan 1 ticket.
- Tim yang menang yang telah mendapatkan tiket bisa gacha untuk mendapatkan sebuah skill card.


# Fitur-Fitur
### POV Player
- Player dapat melihat list booth (nama games, lokasi permainan, jenis booth (single / duel) (DASHBOARD).
- Player dapat melihat list kartu dan jumlah kartu yang mereka miliki selama waktu permainan  (MENU DECK CARD).
- Player dapat melihat total poin yang sudah diperoleh dari masing-masing booth yang sudah dikunjungi  (DASHBOARD).
- Player dapat gacha tiket untuk mendapatkan sebuah skill card (MENU DESK CARD)
- Player dapat melihat timer yang menunjukkan sisa waktu permainan  (DASHBOARD).
- Player dapat menjawab soal-soal penyisihan  (MENU SOAL PENYISIHAN KATEGORI 2).
- Player dapat mengajukan pertanyaan dan melihat jawaban pertanyaan dari panitia melalui fitur QnA  (MENU QNA).

### POV Ketua Panitia 
- Ketua Panitia mengatur durasi keseluruhan Rally Games.
- Ketua Panitia memulai dan menghentikan permainan.
- Ketua Panitia mengatur lokasi berkumpul.

### POV HOD NPLC
- HOD dapat menambah card skill serta mengedit keterangan dan efek kartu  (MENU TAMBAH DAN EDIT KARTU)
- Panitia booth diatur ke boothnya masing-masing oleh admin/HOD NPLC  (MENU DATA BOOTH)
- HOD NPLC bisa melihat dan mengatur detail dan lokasi setiap booth permainan (MENU DATA BOOTH)
- HOD dapat menambah dan mengedit soal penyisihan (beserta jawabannya), serta set jumlah poin untuk setiap soal  (MENU TAMBAH DAN EDIT SOAL)
- HOD dapat melihat leaderboard players (termasuk melihat tim dengan poin tertinggi, dan menentukan pemenang)(MENU LEADERBOARD)
- HOD dapat melihat list pertanyaan dan menjawab pertanyaan yang ditanyakan player melalui fitur QnA  (MENU QNA)

 ### POV Panitia
 - Panitia mendaftarkan tim players yg sedang bermain di boothnya 
   1. Panitia dapat menginput poin yang diperoleh tim yang bermain (single) (MENU INPUT PLAYERS SINGLE POPUP)
   2. Panitia dapat menginput card skill, jika ada dan ingin digunakan oleh players  (MENU INPUT PLAYERS SINGLE)
   3. Panitia dapat menginput tim yang menang diantara dua tim yang bermain (duel)  (MENU INPUT PLAYERS DUEL POPUP)
   4. Lama waktu bermain akan dihitung oleh sistem yang telah diinput oleh panitia (MENU INPUT PLAYERS DUEL & SINGLE)
- Panitia dapat menambah dan mengedit SOP games, dan menambah detail poin bintang untuk games di boothnya  (MENU DATA BOOTH)
- Panitia bisa view list pertanyaan dari player melalui Qna (MENU QNA)

# Installation
1. Clone the repo
   ```sh
   git clone https://github.com/yourusername/Group-3---Project-NPLC.git
   ```
2. Navigate to the project directory
   ```sh
   cd Group-3---Project-NPLC
   ```
3. Install dependencies
   ```sh
   npm install
   ```












