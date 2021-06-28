## XML plugin for filtrating records according to filtration list.

Plugin for filtering records. Exclude records that didn't pass filtration list according value from XPath configuration.

---
### Properties

|Name|Description|Type|Importance|
|---|---|---|---|
|x-path| Specifies the criteria used to match records to be included by this transformation. Use XPath predicate defined in: https://www.w3schools.com/xml/xpath_syntax.asp.| String| High|
|filter.list| Specifies the list of filtration values to be included by this transformation.| List| High |

---
### Test scenario:
Test scenario can be found in `sftp-sink-connector` folder.
