using System;
using System.Collections.Generic;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Data;
using SqlInterface;

namespace cards_web_interface
{
    public partial class _Default : System.Web.UI.Page
    {
        SqlHandler queryResult = new SqlHandler();
        List<string> _columns = new List<string>();

        protected void Page_Load(object sender, EventArgs e)
        {
            queryResult.Table = "completerow";
            ReBindGrid();
            ReBindLists();
        }

        protected void GridView1_SelectedIndexChanged(object sender, EventArgs e)
        {
            GridView1.DataSource = queryResult.PopulateGrid(new Dictionary<string, string>());
            GridView1.DataBind();
        }
        protected void GridView1_PageIndexChanging(object sender, GridViewPageEventArgs e)
        {
            GridView1.PageIndex = e.NewPageIndex;
            GridView1.DataBind();
        }

        protected void ReBindGrid()
        {
            Dictionary<string, string> Search = new Dictionary<string, string>();
            Search.Add("name", "");
            Search.Add("number", "");
            Search.Add("manufacturer", "");
            Search.Add("year", "");
            Search.Add("notes", "");
            Search.Add("price", "");
            if (DDLMfg.SelectedIndex >= 0) { Search["manufacturer"] = DDLMfg.SelectedValue; }
            if (DDLYear.SelectedIndex >= 0) { Search["year"] = DDLYear.SelectedValue; }
            if (DDLPlayerName.SelectedIndex >= 0 && DDLPlayerName.SelectedValue != "Too many results") { Search["name"] = DDLPlayerName.SelectedValue; }
            if (DDLCardNumber.SelectedIndex >= 0 && DDLCardNumber.SelectedValue != "Too many results") { Search["number"] = DDLCardNumber.SelectedValue; }
            GridView1.DataSource = queryResult.PopulateGrid(Search);
            GridView1.DataBind();
        }
        protected void ReBindLists()
        {
            //Add several overrides to prevent massive page gets (limit list items returned)
            //This should but does not yet limit to valid options, if all fields are specified options should be current selections or blanks
            Dictionary<string,string> Queries = new Dictionary<string,string>();
            if ((DDLMfg.SelectedIndex > 0) && (DDLMfg.SelectedValue != null))  { Queries.Add("manufacturer", DDLMfg.SelectedValue); }
            if ((DDLCardNumber.SelectedIndex > 0) && (DDLCardNumber.SelectedValue != null) && (DDLCardNumber.SelectedValue != "Too many results")) { Queries.Add("number", DDLCardNumber.SelectedValue); }
            if ((DDLPlayerName.SelectedIndex > 0) && (DDLPlayerName.SelectedValue != null) && (DDLPlayerName.SelectedValue != "Too many results")) { Queries.Add("name", DDLPlayerName.SelectedValue); }
            if ((DDLYear.SelectedIndex > 0) && (DDLYear.SelectedValue != null)) { Queries.Add("year", DDLYear.SelectedValue); }
            DataTable Values = new DataTable();
            string value = null;
            value = DDLMfg.SelectedValue;
                Values = queryResult.PopFields("manufacturer", Queries);
                Values.Rows.InsertAt(Values.NewRow(), 0);
                if (Values.Rows.Count <= 100)
                {
                    DDLMfg.DataSource = Values;
                    DDLMfg.DataValueField = "manufacturer";
                    DDLMfg.Items.Clear();
                    DDLMfg.DataBind();
                }
                else
                {
                    DDLMfg.Items.Add("Too many results");
                    DDLMfg.DataBind();
                }
                try { DDLMfg.SelectedValue = value; }
                catch { }
                value = DDLYear.SelectedValue;
                Values = queryResult.PopFields("year", Queries);
                Values.Rows.InsertAt(Values.NewRow(), 0);
                if (Values.Rows.Count <= 100)
                {
                    DDLYear.DataSource = Values;
                    DDLYear.DataValueField = "year";
                    DDLYear.Items.Clear();
                    DDLYear.DataBind();
                } else {                    
                    DDLYear.Items.Add("Too many results");
                    DDLYear.DataBind();
                }
                try { DDLYear.SelectedValue = value; }
                catch { }
                value = DDLPlayerName.SelectedValue;
                Values = queryResult.PopFields("name", Queries);
                Values.Rows.InsertAt(Values.NewRow(), 0);
                if (Values.Rows.Count <= 500)
                {

                    DDLPlayerName.DataSource = Values;
                    DDLPlayerName.DataValueField = "name";
                    DDLPlayerName.Items.Clear();
                    DDLPlayerName.DataBind();
                }
                else
                {
                    DDLPlayerName.Items.Clear();
                    DDLPlayerName.Items.Add("Too many results");
                    DDLPlayerName.DataBind();
                }
                try { DDLPlayerName.SelectedValue = value; }
                catch { }
                value = DDLCardNumber.SelectedValue;
                Values = queryResult.PopFields("number", Queries);
                Values.Rows.InsertAt(Values.NewRow(), 0);
                if (Values.Rows.Count <= 500)
                {
                    
                    DDLCardNumber.DataSource = Values;
                    DDLCardNumber.DataValueField = "number";
                    DDLCardNumber.Items.Clear();
                    DDLCardNumber.DataBind();
                }
                else
                {
                    DDLCardNumber.Items.Clear();
                    DDLCardNumber.Items.Add("Too many results");
                    DDLCardNumber.DataBind();
                }
                try { DDLCardNumber.SelectedValue = value; }
                catch { }
                ReBindGrid();
           
        }

        protected void DDLMfg_SelectedIndexChanged(object sender, EventArgs e)
        {
            ReBindLists();
        }

        protected void DDLYear_SelectedIndexChanged(object sender, EventArgs e)
        {
            ReBindLists();
        }

        protected void DDLPlayerName_SelectedIndexChanged(object sender, EventArgs e)
        {
            ReBindLists();
        }

        protected void DDLCardNumber_SelectedIndexChanged(object sender, EventArgs e)
        {
            ReBindLists();
        }

    }
}
