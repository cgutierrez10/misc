using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;
using MySql.Data.MySqlClient;

namespace DataEntry
{
    public partial class Form1 : Form
    {
        MySqlConnection cards;
        bool connect = false;
        //These credentials cannot perform inserts (a necessary function of this application)
        string connectstring = "database=card_prototype;datasource=localhost;user id=read;password=5wimmer";
        string serials;
        public Form1()
        {
            InitializeComponent();
            radioButton1.TabStop = false;
            radioButton2.TabStop = false;
            MySqlDataReader manufacturers, years;
            cards = new MySqlConnection(connectstring);
            cards.Open();
            MySqlCommand populate = new MySqlCommand("SELECT DISTINCT year FROM metadata ORDER BY year ASC", cards);
            years = populate.ExecuteReader();
            while (years.Read())
            {
                comboBox1.Items.Add(years.GetString(0));
            }
            years.Close();
            populate.CommandText = "SELECT DISTINCT manufacturer FROM metadata ORDER BY manufacturer ASC";
            manufacturers = populate.ExecuteReader();
            while (manufacturers.Read())
            {
                comboBox2.Items.Add(manufacturers.GetString(0));
            }
            manufacturers.Close();
            cards.Close();
        }

        private void closeToolStripMenuItem_Click(object sender, EventArgs e)
        {
            if (connect == true) { cards.Close(); }
            Application.Exit();
        }

        int fillgrid()
        {
            string name = textBox1.Text;
            string number = textBox2.Text;
            double valuemult = 0.01 * Convert.ToDouble(textBox8.Text);
            int year = Convert.ToInt32(comboBox1.Text);
            string manufacturer = comboBox2.Text;
            int book = Convert.ToInt32(textBox4.Text);
            int page = Convert.ToInt32(textBox5.Text);
            int pocket = Convert.ToInt32(textBox6.Text);
            dataGridView1.Rows.Add(name, number, valuemult, manufacturer, year, serials, book, page, pocket);
            cards.Open();
            string insertmetadata = String.Format("INSERT INTO metadata (name, number, manufacturer, year, serial) VALUES ('{0:s}', '{1:d}', '{2:s}', '{3:d}', '{4:s}', '{5:d}');", name, number, manufacturer, year, serials);
            string insertuniquedata = String.Format("INSERT INTO uniquedata (container, page, pocket, price, serial_fk0) VALUES ('{0:d}', '{1:d}', '{2:d}', '{3:f}', '{4:s}');", book, page, pocket, valuemult, serials);
            MySqlCommand addmeta = new MySqlCommand(insertmetadata, cards);
            MySqlCommand addunique = new MySqlCommand(insertuniquedata, cards);
            try {
                addmeta.ExecuteReader().Close();
                addunique.ExecuteReader().Close();
            }
            catch (Exception errors)
            {
                Console.WriteLine("Write Error '{0}'\n", errors);
            }
            cards.Close();
            return 0;
        }
        

        bool isopen(MySqlCommand cmd, MySqlConnection connection)
        {
            try
            {
                cmd.ExecuteReader().Close();
            }
            catch (InvalidOperationException)
            {
                Console.WriteLine("Not Connected");
                return (false);
            }
            catch (MySqlException)
            {
                Console.WriteLine("Invalid command:" + cmd + "\n");
                return (false);
            }
            return (true);
        }

        private void radioButton1_CheckedChanged(object sender, EventArgs e)
        {
            textBox4.Enabled = false;
            textBox5.Enabled = false;
            textBox6.Enabled = false;
            radioButton1.TabStop = false;
            radioButton2.TabStop = false;
        }

        private void radioButton2_CheckedChanged(object sender, EventArgs e)
        {
            textBox4.Enabled = true;
            textBox5.Enabled = true;
            textBox6.Enabled = true;
            radioButton1.TabStop = false;
            radioButton2.TabStop = false;
        }

        private void exitToolStripMenuItem_Click(object sender, EventArgs e)
        {
            if (connect == true) { cards.Close(); }
            Application.Exit();
        }

        private void button1_Click(object sender, EventArgs e)
        {
            if (validata()) { return; }
            if (radioButton1.Checked == true) {
                int pocket = Convert.ToInt32(textBox6.Text);
                if (pocket == 9) { pocket = 0; textBox5.Text = Convert.ToString(Convert.ToInt32(textBox5.Text)+1); }
                pocket++;
                textBox6.Text = Convert.ToString(pocket);
            }
            textBox1.Text = "";
            textBox2.Text = "";
            textBox7.Text = "";
        }

        void generate_serial(string mfg, int year, string number)
        {
            switch(mfg) {
                case "Topps":
                    serials = "a";
                    break;
                case "Donruss":
                    serials = "1";
                    break;
                case "Bowman":
                    serials = "2";
                    break;
                case "Fleer":
                    serials = "3";
                    break;
                case "Upper Deck":
                    serials = "4";
                    break;
                case "Donruss Triple Play":
                    serials = "5";
                    break;
                case "Ultra":
                    serials = "6";
                    break;
                case "Score":
                    serials = "7";
                    break;
                case "Topps Stadium Club":
                    serials = "8";
                    break;
                case "Pacific Collection":
                    serials = "9";
                    break;
                default: serials = "ERR"; break;
           }
           serials = String.Concat(serials, comboBox1.Text, textBox2.Text);
           return;
        }
        bool validata()
        {
            try {
                Convert.ToInt32(textBox8.Text);
            }
            catch(Exception errors) {
                Console.WriteLine("Error '{0}'\n", errors);
                return true;
            }
            generate_serial(comboBox2.Text, Convert.ToInt32(comboBox1.Text), textBox2.Text);
            if (Convert.ToBoolean(fillgrid())) {            
                Console.WriteLine("Error in data fill.\n");
                return true;
            }
            return false;
        }

        private void deleteRowToolStripMenuItem_Click(object sender, EventArgs e)
        {
            //Works on sequential rows only at present
            int i = 0;
            while (dataGridView1.SelectedRows.Count > i )
            {
                string deleterow = String.Format("DELETE FROM value WHERE Serial_FK1='{0}';DELETE FROM uniquedata WHERE Serial_fk0='{0}';DELETE FROM metadata WHERE Serial='{0}';", dataGridView1[6, i].Value);
                MySqlCommand deletecmd = new MySqlCommand(deleterow, cards);
                cards.Open();
                try
                {
                    deletecmd.ExecuteReader().Close();
                }
                catch (Exception errors)
                {
                    Console.WriteLine("Write Error '{0}'\n", errors);
                }
                cards.Close();
                try
                {
                    dataGridView1.Rows.Remove(dataGridView1.SelectedRows[i]);
                }
                catch (Exception errors)
                {
                    Console.WriteLine("Deletion error '{0}'\n", errors);
                }
                i++;
            }
        }

        private void fillDataToolStripMenuItem_Click(object sender, EventArgs e)
        {
            //MySql.Data.MySqlClient.MySqlDataReader years;
            cards = new MySqlConnection(connectstring);
            cards.Open();
            fillgrid(cards);
            cards.Close();
        }
        
        int fillgrid(MySqlConnection cards)
        {
            MySqlDataReader data;
            string command = "SELECT metadata.name, metadata.number, uniquedata.price, metadata.manufacturer, metadata.year, metadata.serial, uniquedata.container, uniquedata.page, uniquedata.location FROM metadata, uniquedata WHERE metadata.serial=uniquedata.serial_fk0";
            Console.WriteLine(command);
            MySqlCommand populate = new MySqlCommand(command, cards);
            data = populate.ExecuteReader();
            int count = 0;
            while (data.Read())
            {
                dataGridView1.Rows.Add(data.GetValue(0), data.GetValue(1), data.GetValue(2), data.GetValue(3), data.GetValue(4), data.GetValue(5), data.GetValue(6), data.GetValue(7), data.GetValue(8));
                count++;
            }
            data.Close();
            return count;
        }

        private void Form1_ResizeEnd(object sender, EventArgs e)
        {
            dataGridView1.Width = this.ClientRectangle.Width - 22;
            dataGridView1.Height = this.ClientRectangle.Height - 157;
        }
    }
}