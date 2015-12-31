using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Drawing;
using System.Text;
using System.Data;
using System.Windows.Forms;
using MySql.Data.MySqlClient;
using SqlInterface;

namespace CardViewer
{
    public partial class Form1 : Form
    {
        bool connect = false;
        string manufacturer;    //Stores manufacturer text for use in datagridview fulfilling
        string year;    //Stores year text for use in datagridview fulfilling
        int i = 0;  //This appears to be related to the workings of the datagridview?
        Dictionary<string, string> Fields = new Dictionary<string, string>();
        SqlHandler QueryResults = new SqlHandler();

        public Form1()
        {
            InitializeComponent();
            Fields.Add("name", "name");
            Fields.Add("number", "number");
            Fields.Add("Manufacturer", "Manufacturer");
            Fields.Add("Year", "Year");
            Fields.Add("Price", "Price");
            Fields.Add("Serial", "Serial");
            Fields.Add("Validate", "Validate");
        }

        private void closeToolStripMenuItem_Click(object sender, EventArgs e)
        {
            Application.Exit();
        }
        
        //This function improperly handles null set returns, ALL requests through sqlhandler should safely accept null QueryResults
        private void connectToolStripMenuItem_Click(object sender, EventArgs e)
        {
            if (connect == false)
            {
                connectToolStripMenuItem.Text = "Disconnect";
                toolStripStatusLabel1.Text = "Connected";
                connect = true;
                DataTable Results = new DataTable();
                //SqlHandler QueryResults = new SqlHandler();
                QueryResults.Table = "book";
                Results = QueryResults.PopFields("isbn");
                Results.Rows.InsertAt(Results.NewRow(), 0);
                BookBox.DataSource = Results;
                BookBox.DisplayMember = "isbn";
                QueryResults.Table = "metadata";
                Results = QueryResults.PopFields("year");
                Results.Rows.InsertAt(Results.NewRow(), 0);
                YearBox.DataSource = Results;
                YearBox.DisplayMember = "year";
                QueryResults.Table = "metadata";
                Results = QueryResults.PopFields("manufacturer");
                Results.Rows.InsertAt(Results.NewRow(), 0);
                MfgBox.DataSource = Results;
                MfgBox.DisplayMember = "manufacturer";
                MfgBox.Enabled = true;
                YearBox.Enabled = true;
                BookBox.Enabled = true;
            }
            else
            {
                connect = false;
                connectToolStripMenuItem.Text = "Connect";
                toolStripStatusLabel1.Text = "Not connected";
                MfgBox.DataSource = null;
                YearBox.DataSource = null;
                BookBox.DataSource = null;
                MfgBox.Enabled = false;
                YearBox.Enabled = false;
                BookBox.Enabled = false;
                TitleBox.Text = null;
                EditionBox.Text = null;
                BkYearBox.Text = null;
            }

        }

        private void checkBox1_CheckedChanged(object sender, EventArgs e)
        {
            if (checkBox1.Checked == true)
            {
                TitleBox.Enabled = false;
                EditionBox.Enabled = false;
                BkYearBox.Enabled = false;
                button2.Enabled = false;
                button3.Enabled = false;
            }
            else
            {
                TitleBox.Enabled = true;
                EditionBox.Enabled = true;
                BkYearBox.Enabled = true;
                button2.Enabled = true;
                button3.Enabled = true;
            }
        }


        private void MfgBox_SelectedIndexChanged(object sender, EventArgs e)
        {
            manufacturer = MfgBox.SelectedText;
        }

        private void YearBox_SelectedIndexChanged(object sender, EventArgs e)
        {
            year = YearBox.SelectedText;
        }

        
        private void BookBox_SelectedIndexChanged(object sender, EventArgs e)
        {
            SqlHandler BookQuery = QueryResults;
            BookQuery.ValidCols = new List<string> { "title", "edition", "publish_year" };
            BookQuery.Table = "book";
            Dictionary<string,string> Selection = new Dictionary<string,string>();
            Selection.Add("isbn", BookBox.Text);
            if (BookQuery.PopFields("title", Selection).Rows.Count != 0)
            {
                TitleBox.Text = BookQuery.PopFields("title", Selection).Rows[0][0].ToString();
                EditionBox.Text = BookQuery.PopFields("edition", Selection).Rows[0][0].ToString();
                BkYearBox.Text = BookQuery.PopFields("publish_year", Selection).Rows[0][0].ToString();
                string watchpoint = BookQuery.PopFields("publish_year", Selection).ToString();
            }
        }

        private void button1_Click(object sender, EventArgs e)
        {
            
        }

        private void button2_Click(object sender, EventArgs e)
        {
            Console.WriteLine(String.Format("SELECT COUNT(*) FROM book WHERE ISBN='{0}';", BookBox.Text));
            try
            {
            }
            catch (Exception errors)
            {
                Console.WriteLine("Error with book update.\n {0}\n", errors);
            }
        }
        
        private void button3_Click(object sender, EventArgs e)
        {
            string deletebook = "DELETE FROM book WHERE ISBN=" + BookBox.Text;
            Console.WriteLine(deletebook);
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
            return(true);
        }

        int fillgrid(MySqlDataReader data)
        {

            dataGridView1.Columns.Clear();
            dataGridView1.Columns.Add("Name", "Name");
            dataGridView1.Columns.Add("Number", "Number");
            dataGridView1.Columns.Add("Manufacturer", "Manufacturer");
            dataGridView1.Columns.Add("Year", "Year");
            dataGridView1.Columns.Add("Price", "Price");
            dataGridView1.Columns.Add("Serial", "Serial");
            dataGridView1.Columns.Add("Validate", "Validate");
            int count = 0;
            while (data.Read())
            {
                dataGridView1.Rows.Add(data.GetValue(0), data.GetValue(1), data.GetValue(2), data.GetValue(3), data.GetValue(4), data.GetValue(5));
                count++;
            }
            data.Close();
            return count;
        }

        private void YearBox_TextChanged(object sender, EventArgs e)
        {
            year = null;
        }
        private void MfgBox_TextChanged(object sender, EventArgs e)
        {
            manufacturer = null;
        }

        private void BookBox_TextChanged(object sender, EventArgs e)
        {
        }

        private void button6_Click(object sender, EventArgs e)
        {
            try
            {
            }
            catch (Exception errors)
            {
                Console.WriteLine("Price entry error! {0}\n", errors);
            }
            i++;
            dataGridView1.CurrentCell = dataGridView1.Rows[i].Cells[0];
        }

        private void dataGridView1_CellContentClick(object sender, DataGridViewCellEventArgs e)
        {
            i = dataGridView1.CurrentCell.RowIndex;
        }

        private void deleteRowToolStripMenuItem_Click(object sender, EventArgs e)
        {
            //Works on sequential rows only at present
            int i = 0;
            while (dataGridView1.SelectedRows.Count > i)
            {
                string deleterow = String.Format("DELETE FROM value WHERE Serial_FK1='{0}';DELETE FROM uniquedata WHERE Serial_fk0='{0}';DELETE FROM metadata WHERE Serial='{0}';", dataGridView1[4, i].Value);
                try
                {
                }
                catch (Exception errors)
                {
                    Console.WriteLine("Write Error '{0}'\n", errors);
                }
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
        private void ConfigureDialog()
        {
            Application.Run(new SqlHandler.DialogBoxForm1());
        }

    }
}








