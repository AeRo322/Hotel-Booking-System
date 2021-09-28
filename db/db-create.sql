mysqldump hotel > dump.sql
mysqladmin create hotelTest
mysql hotelTest < dump.sql