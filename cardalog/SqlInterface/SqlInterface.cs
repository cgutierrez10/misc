using System;
using System.Data;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using MySql.Data.MySqlClient;
using System.IO;

namespace SqlInterface
{
    public class SqlHandler
    {
        private string _ConnectString;
        private string _SqlUser;
        private string _SqlUserPassword;
        private int _SqlPort;
        private string _SqlHostIP;
        private string _SqlDB;
        private string _DBTable;
        private List<string> _ValidColumns = new List<string>() { "manufacturer", "name", "number", "year", "", "price", "notes", "isbn" };
        private bool _NewConnectString = false;
        //private MySqlDataAdapter _SQLDB = new MySqlDataAdapter();
        
        //public request(
        public List<string> ValidCols
        {
            set { _ValidColumns = value; }
            internal get { return (null); }
        }
        public string User {
            set { _SqlUser = value; _NewConnectString = true; }
            internal get { return(""); }
        }
        public string UserPassword {
            set { _SqlUserPassword = value; _NewConnectString = true; }
            internal get { return (""); }
        }
        public int Port
        {
            set { _SqlPort = value; _NewConnectString = true; }
            internal get { return (0); }
        }
        public string Host
        {
            set { _SqlHostIP = value; _NewConnectString = true; }
            internal get { return (""); }
        }
        //Include IsValidDbase check?
        public string DBase
        {
            set { _SqlDB = value; _NewConnectString = true; }
            internal get { return (""); }
        }
        //Include IsValidTable check?
        public string Table
        {
            set { _DBTable = value; _NewConnectString = true; }
            internal get { return (""); }
        }

        public SqlHandler()
        {
            //Fill from config file
            TextReader Config = new StreamReader("c:\\sql.config");
            List<string> ConfigOpts = new List<string> ((Config.ReadToEnd()).Split(':'));
            _SqlUser = ConfigOpts[0];
            _SqlUserPassword = ConfigOpts[1];
            _SqlHostIP = ConfigOpts[2];
            _SqlDB = ConfigOpts[3];
            _NewConnectString = true;
        }

        ~SqlHandler()
        {

        }

        private string SetConnectString() {
            if (_NewConnectString)
            {
                _ConnectString = "Server=" + _SqlHostIP + ";Database=" + _SqlDB + ";Uid=" + _SqlUser + ";Pwd=" + _SqlUserPassword;
                if (_SqlPort != 0) { _ConnectString = _ConnectString + ";Port=" + _SqlPort; } //Default port (no port definition in connectstring) unless specified
                _NewConnectString = false;
                return _ConnectString;
            }
            else
            {
                return _ConnectString;
            }
        }


        public DataTable PopFields(string ReturnColumn)
        {
            if (!__IsValidField(ReturnColumn)) { return (__ReturnNull()); }
            MySqlCommand Command = new MySqlCommand("SELECT DISTINCT " + ReturnColumn + " FROM " + _DBTable + " ORDER BY " + ReturnColumn + ";");
            return (__ProcessSql(Command));
        }

        public DataTable PopFields(string ReturnColumn, Dictionary<string, string> Selections) 
        {
            if (Selections.Count == 0) { return(PopFields(ReturnColumn)); }
            
            if (!(__IsValidField(ReturnColumn))) { return (__ReturnNull()); }
            //if () { return PopFields(ReturnColumn); }
            string CommandString = "SELECT DISTINCT " + ReturnColumn + " FROM " + _DBTable + " WHERE ";
            MySqlCommand Command = new MySqlCommand();
            
            foreach (string k in Selections.Keys) {
                if (k != ReturnColumn) { CommandString = CommandString + k + "=@" + k + " AND "; }
            }
            CommandString = CommandString.Substring(0,CommandString.Length-4);
            CommandString = CommandString + " ORDER BY " + ReturnColumn + ";";
            Command.CommandText = CommandString;
            foreach (string k in Selections.Keys) {
                Command.Parameters.AddWithValue("@" + k, Selections[k]);
            }
            
            return(__ProcessSql(Command));
        }

        public Boolean InsertData(Dictionary<string,string> Values)
        {

            return(false);
        }

        //IsValidField function set confirms no inappropriate fields are referencable
        //Any invalid fields should return a value that leads calling code to return null table
        //Effectively masks table valid fields down to programmer intended valid fields
        public bool __IsValidField(List<string> ColumnNames)
        {
            foreach (string k in ColumnNames)
            {
                if (!(_ValidColumns.Contains(k))) { return (false); }
            }
            return (true);
        }

        public bool __IsValidField(string ColumnName) {
            return (_ValidColumns.Contains(ColumnName));
        }

        //Stub, call selectconstruct to select for necessary values
        public DataTable PopulateGrid(Dictionary<string, string> SearchVals)
        {
            //Ready-To-Use wrap?
            return(__SelectConstruct(SearchVals));
        }

        //Preserving order assemble list elements into single string adding , and spaces where appropriate
        private string ListJoin(List<string> Fragments)
        {
            string statement = "";            
            while (Fragments.Count >= 2)
            {
                if (((Fragments.IndexOf("SELECT") - 1) * (Fragments.IndexOf("WHERE") - 1) * (Fragments.IndexOf("DISTINCT") - 1) * (Fragments.IndexOf("ORDER BY") - 1) * (Fragments.IndexOf("FROM " + _DBTable) - 1)) > 0) {
                    Fragments[0] = Fragments[0] + ",";
                }
                statement = statement + " " + Fragments[0];
                if (Fragments.Count == 2)
                {
                    if ((Fragments[0] == "WHERE") || (Fragments[0] == "SORT BY"))
                    {
                        statement = statement + ", ";
                    }
                    statement = statement + " " + Fragments[1];
                    Fragments.Remove(Fragments[0]);
                }
                Fragments.Remove(Fragments[0]);
            }

            return statement;
        }
        private DataTable __ProcessSql(MySqlCommand SqlCommand)
        {
            MySqlConnection CardDB = new MySqlConnection(SetConnectString());
            SqlCommand.Connection = CardDB;
            MySqlDataAdapter Server = new MySqlDataAdapter(SqlCommand);
            DataTable queryResult = new DataTable();
            try
            {
            Server.Fill(queryResult);
            }
            //Any and all MySql exceptions are caught and handled by returning a null result, also log errors for debugging and security
            catch (MySqlException) 
            {
                queryResult = __ReturnNull();
            }
            return queryResult;
        }

        //Prepares an insert statement, checks for permissions (from DB and from calling code)
        private DataTable __InsertConstruct(Dictionary<string, string> Values)
        {

            return (__ReturnNull());
        }


        //Prepares select statement
        private DataTable __SelectConstruct(Dictionary<string, string> SearchVals) {

            if (SearchVals.Count == 0) { return (__ReturnNull()); }
            if (!(__IsValidField(SearchVals.Keys.ToList<string>()))) { return (__ReturnNull()); }
            //if () { return PopFields(ReturnColumn); }
            string CommandString = "SELECT DISTINCT " + string.Join(", ", SearchVals.Keys.ToArray<string>()) + " FROM " + _DBTable + " WHERE ";
            MySqlCommand Command = new MySqlCommand();

            foreach (string k in SearchVals.Keys)
            {
                if (SearchVals[k] != "" && SearchVals[k] != null) { CommandString = CommandString + k + "=@" + k + " AND "; }
            }
            CommandString = CommandString.Substring(0, CommandString.Length - 4);
            CommandString = CommandString + " ORDER BY " + SearchVals.Keys.ToArray<string>()[0] + ";";
            Command.CommandText = CommandString;
            foreach (string k in SearchVals.Keys)
            {
                if (SearchVals[k] != "" && SearchVals[k] != null) { Command.Parameters.AddWithValue("@" + k, SearchVals[k]); }
            }

            return (__ProcessSql(Command));
        }

        //Generates and returns a null resultset to fill stubs during development and exceptions during runtime
        private DataTable __ReturnNull()
        {
            DataTable result = new DataTable();
            result.Columns.Add("Null");
            result.Rows.Add("Null");
            return result;
        }

    }
}