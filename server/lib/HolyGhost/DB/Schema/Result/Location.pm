use utf8;
package HolyGhost::DB::Schema::Result::Location;

# Created by DBIx::Class::Schema::Loader
# DO NOT MODIFY THE FIRST PART OF THIS FILE

use strict;
use warnings;

use base 'DBIx::Class::Core';
__PACKAGE__->table("location");
__PACKAGE__->add_columns(
  "id",
  {
    data_type         => "integer",
    is_auto_increment => 1,
    is_nullable       => 0,
    sequence          => "location_id_seq",
  },
  "lat",
  { data_type => "double precision", is_nullable => 0 },
  "lon",
  { data_type => "double precision", is_nullable => 0 },
  "title",
  { data_type => "varchar", is_nullable => 0, size => 128 },
  "description",
  { data_type => "text", is_nullable => 1 },
);
__PACKAGE__->set_primary_key("id");


# Created by DBIx::Class::Schema::Loader v0.07043 @ 2015-08-29 08:56:08
# DO NOT MODIFY THIS OR ANYTHING ABOVE! md5sum:M/zkXaod1YkB1PnPdSeuWg


# You can replace this text with custom code or comments, and it will be preserved on regeneration
1;
